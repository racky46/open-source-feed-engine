/* Copyright 2008 Hycel Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qagen.osfe.engine.monitor;

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.common.FeedConstants;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.param.MonitorQueueParam;
import com.qagen.osfe.dataAccess.service.FeedFileService;
import com.qagen.osfe.dataAccess.service.FeedQueueService;
import com.qagen.osfe.dataAccess.service.FeedService;
import com.qagen.osfe.dataAccess.service.FeedMonitorService;
import com.qagen.osfe.dataAccess.vo.*;
import com.qagen.osfe.core.FeedErrorException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author: Hycel Taylor
 * <p/>
 * FeedQueueMonitor does the heavy lifting of managing multiple feed engine
 * threads by monitoring multiple queues that contain information about the
 * feeds that should be launched from a given queue.
 * <p/>
 * The FeedQueueMonitor uses the singleton design pattern to ensure that only
 * one instance of a FeedQueueMonitor is instantiated for a given JVM.
 * <p/>
 * All instances of FeedQueueMonitor share a common persistant storage
 * table, t_feed_queue, which allows each instance to manange its queues.
 * Thus, each time an instance of FeedQueueMonitor is launched from a new JVM,
 * the environment variable, MONITOR_ID must be specified.  Specifiying the
 * MONITOR_ID allows the FeedQueueMonitor to identify and manage its queues
 * located in t_feed_queue.
 */
public class FeedQueueMonitor implements FeedQueueMonitorMBean {
  public static final String MONITOR_ID = "MONITOR_ID";
  public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";
  public static final int SECONDS = 15;
  public static final int SLEEP_TIME = 1000 * SECONDS;

  private static Map<String, FeedQueue> activeMap;
  private static Map<String, Queue<FeedQueue>> queueMap;

  private static Set<String> onHold;
  private SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);

  private static FeedQueueMonitor singleton;
  private static FeedQueueService queueService;
  private static FeedService feedService;
  private static FeedFileService feedFileService;
  private static FeedMonitor feedMonitor;

  private static final String wait = "wait";
  private static String monitorId;

  private static Log logger = Log.getInstance(FeedQueueMonitor.class);

  /**
   * Constructor
   */
  public FeedQueueMonitor() {
    this.monitorId = System.getenv(MONITOR_ID);

    activeMap = new HashMap<String, FeedQueue>();
    queueMap = new HashMap<String, Queue<FeedQueue>>();
    onHold = new HashSet<String>();
    feedService = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);
    feedFileService = (FeedFileService) DataAccessContext.getBean(FeedFileService.SERVICE_ID);
    queueService = (FeedQueueService) DataAccessContext.getBean(FeedQueueService.SERVICE_ID);

    initFeedMonitor(monitorId);
    initializeQueue();
  }

  /**
   * Uses the singleton design pattern to instantiate only one instance
   * of FeedQueueMonitor.
   *
   * @return single instance of FeedQueueMonitor.
   */
  public static FeedQueueMonitor getInstance() {
    if (singleton == null) {
      synchronized (wait) {
        if (singleton == null) {
          singleton = new FeedQueueMonitor();
        }
      }
    }
    return singleton;
  }

  private void initFeedMonitor(String monitorId) {
    final FeedMonitorService service = (FeedMonitorService) DataAccessContext.getBean(FeedMonitorService.SERVICE_ID);

    feedMonitor = service.findByPrimaryId(monitorId);

    if (feedMonitor == null) {
      throw new FeedErrorException("Feed Monitor Id, " + monitorId + ", is not define in table t_feed_monitor.");
    }
  }

  /**
   * Initializes a queue by loading data from t_feed_queue
   */
  private void initializeQueue() {
    final List<FeedQueue> feedQueues = queueService.findAll(monitorId);

    for (FeedQueue feedQueue : feedQueues) {
      add(feedQueue, false);
    }
  }

  /**
   * Performs the following actions:
   * <ul>
   * <li>Creates the queue if one does not exist for the given queueId.
   * <li>Persist the feedQueue object to database cache if usePersistance is true.
   * <li>Adds the feedQueue object to the queue.
   * <li>Calls launchRequest for the given queueId.
   * </ul>
   *
   * @param feedQueue      contains the information about the queue to create and
   *                       the feed to launch.
   * @param usePersistance speicifies whether the feedQueue object being added to
   *                       the queue in memory should also be persisted to the
   *                       database. A queue that is being reinitialized from the
   *                       database should not persist the feedQueue object again.
   */
  private synchronized void add(FeedQueue feedQueue, Boolean usePersistance) {
    logAddRequest(feedQueue);

    final String queueId = feedQueue.getFeedQueueType().getFeedQueueTypeId();
    Queue<FeedQueue> queue = queueMap.get(queueId);

    // Create the queue if one does not exist for the given queueId.
    if (queue == null) {
      queue = new LinkedBlockingQueue<FeedQueue>();
      queueMap.put(queueId, queue);
    }

    // usePersistance will be true if the feedMappedQueue was not already pulled from persistance.
    if (usePersistance) {
      logAddPersisted(feedQueue);
      queueService.insert(feedQueue);
    }

    // Add the feedQueue object to the queue.
    queue.add(feedQueue);

    launchRequest(queueId);
  }

  /**
   * Launches a FeedEngine object in its own thread.
   *
   * @param queueId identifies the queue to check to see if a feed needs launching.
   */
  private void launchRequest(String queueId) {
    // A launch cannot be made if a feed of the given queueId is already active.
    if (activeMap.containsKey(queueId))
      return;

    // A launch cannot be made if a feed queue of the given queueId is in a holding state.
    if (onHold.contains(queueId))
      return;

    // Can't launch if a queue of the given type does not exists.
    final Queue<FeedQueue> queue = queueMap.get(queueId);
    if (queue == null)
      return;

    // Retrieve feed information.
    final FeedQueue feedQueue = queue.poll();

    // Prepare to launch feed.
    if (feedQueue != null) {
      final String feedFileName = feedQueue.getFeedFileName();
      final FeedQueueFeedLauncher feedLauncher = new FeedQueueFeedLauncher(queueId, feedFileName);

      // States that a feed of the given feed type is now being processed.
      activeMap.put(queueId, feedQueue);

      // Removed FeedMappedeQueue record from database cache.
      queueService.delete(feedQueue);

      final Thread thread = new Thread(feedLauncher);
      thread.start();
    }
  }

  /**
   * Adds add new FeedMappedQueue object to the feed queue. Then check to see
   * if the oldest FeedMappedQueue object of the given feedId type can be launched.
   *
   * @param feedId       identifies the feed.
   * @param feedFileName identifies the name and location of the feed file to process.
   */
  public void add(String feedId, String feedFileName) {
    final Feed feed = feedService.findByPrimaryId(feedId);

    if (feed != null) {
      final FeedQueueState feedQueueState = new FeedQueueState(FeedConstants.FEED_QUEUE_STATE.waiting.getValue());
      final FeedQueueType feedQueueType = feed.getFeedQueueType();
      final FeedQueue feedQueue = new FeedQueue(feedQueueType, feedMonitor, feedQueueState, feed, feedFileName);
      add(feedQueue, true);
    } else {
      final String message =
        "Unable to process feed file, " + feedFileName + ". Feed for feedId, " + feedId + ", was not found.";
      logger.error(message);
    }
  }

  /**
   * Notifies monitor that a feed of a the given queueId type is no longer
   * being processed by the feed engine.  Then attempts to launch another
   * feed of the given feed type, if one exists.
   *
   * @param queueId identifies the queue that is no longer active.
   */
  public synchronized void notifyQueue(String queueId) {
    activeMap.remove(queueId);
    launchRequest(queueId);
  }

  /**
   * Places a queue, for the the given queueId, in a hold or non hold state.
   * When a the condition is true, the givenqueue can still receive feeds
   * for processessing but may not release any feeds for processing.
   *
   * @param queueId   identifies the queue to place in a hold or non hold state.
   * @param condition set to true to place all queues on hold.
   */
  public void changeOnHoldState(String queueId, boolean condition) {
    if (condition) {
      if (!onHold.contains(queueId)) {
        onHold.add(queueId);
      }
    } else {
      onHold.remove(queueId);
    }
  }

  /**
   * Places all queues in a hold or non hold state.  When a the condition is
   * true, all queues can still receive feeds for processessing but may not
   * release any feeds for processing.
   *
   * @param condition set to true to place all queues on hold.
   */
  public void changeOnHoldState(boolean condition) {
    for (String queueId : onHold) {
      changeOnHoldState(queueId, condition);
    }
  }

  /**
   * Empties a given queue in memory and in the database cache.
   *
   * @param queueId identifies the queue to empty.
   */
  public void clearQueue(String queueId) {
    Queue<FeedQueue> queue = queueMap.get(queueId);

    // If a queue of the given queueId does not exist, creat on and add it to the map.
    if (queue != null) {
      queue.clear();
    }
    queueService.findByMonitorIdQueueId(new MonitorQueueParam(monitorId, queueId));
  }

  /**
   * Empties all queus in memory and in the database cache.
   */
  public void clearQueues() {
    final Set<String> queueSet = queueMap.keySet();

    for (String queueId : queueSet) {
      queueMap.get(queueId).clear();
    }

    queueService.deleteAll(monitorId);
  }

  /**
   * Places the given feed at the front of it mapped queue for processing.
   *
   * @param feedId       used to map to a specific queue.
   * @param feedFileName the name of the feed file to process.
   */
  public void launchFeed(String feedId, String feedFileName) {
    logger.info("Launching feed for, " + feedFileName + ".");

    changeOnHoldState(true);
    final Feed feed = feedService.findByPrimaryId(feedId);
    final String queueId = feed.getFeedQueueType().getFeedQueueTypeId();

    waitTillFeedNotActive(queueId);

    final FeedQueueType feedQueueType = feed.getFeedQueueType();
    final FeedQueueState feedQueueState = new FeedQueueState(FeedConstants.FEED_QUEUE_STATE.waiting.getValue());
    final FeedQueue feedQueue = new FeedQueue(feedQueueType, feedMonitor, feedQueueState, feed, feedFileName);
    final FeedQueueFeedLauncher feedLauncher = new FeedQueueFeedLauncher(feedId, feedFileName);

    // States that a feed of the given feed type is now being processed.
    activeMap.put(feedId, feedQueue);

    final Thread thread = new Thread(feedLauncher);
    thread.start();

    changeOnHoldState(false);
  }

  /**
   * Places a given feed which is in a Failed state into a Retry state and
   * places the given feed at the front of it mapped queue for processing
   *
   * @param feedFileId - identifies the feed to fail.
   */
  public void retryFeed(Integer feedFileId) {
    logger.info("Retrying feed, " + feedFileId + ".");

    final FeedFile feedFile = feedFileService.findByPrimaryId(feedFileId);
    if (feedFile != null) {
      changeOnHoldState(true);

      final Feed feed = feedFile.getFeed();
      final String feedId = feed.getFeedId();
      final String queueId = feed.getFeedQueueType().getFeedQueueTypeId();

      waitTillFeedNotActive(queueId);

      final FeedQueueType feedQueueType = feed.getFeedQueueType();
      final FeedQueueState feedQueueState = new FeedQueueState(FeedConstants.FEED_QUEUE_STATE.waiting.getValue());
      final FeedQueue feedQueue = new FeedQueue(feedQueueType, feedMonitor, feedQueueState, feed, feedFile.getFeedFileName());
      final FeedQueueRetryFeedLauncher retryFeedLauncher = new FeedQueueRetryFeedLauncher(feedFileId);

      // States that a feed of the given feed type is now being processed.
      activeMap.put(feedId, feedQueue);

      final Thread thread = new Thread(retryFeedLauncher);
      thread.start();

      changeOnHoldState(false);
    } else {
      final String message = "Unable to find feed file for feedFileId, " + feedFileId + ".";
      logger.error(message);
    }
  }

  /**
   * Goes into a wait state until the given queue is no longer in an
   * active state.
   *
   * @param queueId identifies the queue to monitor.
   */
  private void waitTillFeedNotActive(String queueId) {
    final FeedQueue feedQueue = activeMap.get(queueId);
    final String feedFileName = (feedQueue != null) ? feedQueue.getFeedFileName() : null;

    while (activeMap.containsKey(queueId)) {
      final String message =
        "Feed file, " + feedFileName + " is being processed for queueId " + queueId + ".  Checking every " + SECONDS + " seconds...";
      logger.info(message);
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        // do nothing.
      }
    }
  }

  /**
   * Echos log information.
   *
   * @param feedQueue contains the information to log.
   */
  private void logAddRequest(FeedQueue feedQueue) {
    StringBuilder builder = new StringBuilder();
    builder
      .append("***** Adding to Queue: ")
      .append(feedQueue.getFeed().getFeedId())
      .append(" | ")
      .append(dateFormat.format(feedQueue.getEntryTime()))
      .append(" | ")
      .append(feedQueue.getFeedFileName());

    logger.debug(builder.toString());
  }

  /**
   * Echos log information.
   *
   * @param feedQueue contains the information to log.
   */
  private void logAddPersisted(FeedQueue feedQueue) {
    StringBuilder builder = new StringBuilder();
    builder
      .append("***** Persisted to Queue: ")
      .append(feedQueue.getFeedQueueId())
      .append(" | ")
      .append(dateFormat.format(feedQueue.getEntryTime()));

    logger.debug(builder.toString());
    System.out.println(builder.toString());
  }

  /**
   * Places all feed queues on hold.<p>
   * Waits for the completion of all actively running queues and sends a notification that all feeds have completed.
   */
  public void shutDownFeeds() {
    logger.info("Initiating a shutdown of all feeds...");

    changeOnHoldState(true);

    while (!activeMap.isEmpty()) {
      logger.info("Currently " + activeMap.size() + " feed(s) are still active.  Checking every " + SECONDS + " seconds...");
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException e) {
        // do nothing.
      }
    }

    logger.info("All feeds are shutdown... Remaining feeds are on hold.");
  }

  /**
   * Places all feed queues on hold.<p>
   * Waits for the completion of all actively running queues and sends a notification that all feeds have completed.<p>
   * Sends a nofiification that it is shutting down.<p>
   * Excecutes a System.exit(0);
   */
  public void shutdown() {
    shutDownFeeds();
    logger.info("Initating complete shutdown of the MappedQueueMonitor. Good bye...");
    System.exit(0);
  }
}
