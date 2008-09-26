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
package com.qagen.osfe.engine;

import static com.qagen.osfe.common.FeedConstants.*;
import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.*;
import com.qagen.osfe.core.loaders.BeanLoader;
import com.qagen.osfe.core.utils.FeedFileHelper;
import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedGroup;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.dom4j.Document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Author: Hycel Taylor
 * <p/>
 * AbstractFeedEngine provides the base behavior and contract for all
 * concrete feed engines.  AbstractFeedEngine provides a common framework
 * and design patterns for three primary types of feed processing:
 * <ul>
 * <li>Data Retriever Engines - Engines that retrieve data from internal or
 * external sources (e.g., retrieving data via socket, FTP, Web Service).
 * Retrieved files may also need to be prepared for processing (e.g., data
 * may need to be unzipped, unencrypted, concatenated, etc).
 * <li>Inbound Data Processing Engines - A raw data that has been retrieved
 * and prepared for processing is parsed, passed through phases and
 * processed into meaningful data (e.g., filtered, calculated and sent to
 * persistent storage).
 * <li>Outbound Data Creation Engines - Data is pull from database tables or
 * files, processed and placed into a feed file in a specified format
 * (e.g., sending an enrollment response to complete the contract from a given
 * enrollment request feed).
 * </ul>
 * <p/>
 * AbstractFeedEngine will prepare a feed for processing by performing the
 * following initialization sequence:
 * <ol>
 * <li>initContext(feedId, feedFileName) - store the feedId and filename as
 * instance variables, place the filename in the context and instantiate the
 * feedJobManager.
 * <li> initFeedRecord() - Find the Feed record for the given feedId. Verify
 * that the feedId is valid and check it's validation rules to verify that
 * the feed can be processed.
 * <li>initFullPathAndFeedFileName() - Define the complete location of the
 * feed file by concatenating the directory path defined in the Feed record
 * with the given feed file name.
 * <li>initConfigFile() - Load the feed configuration document defined in the
 * Feed record and place a reference to the document in the engine context.
 * <li>loadBeans() -
 * <li>initialize() - Allows a concrete implementation of the AbstractFeedEngine
 * to perform its own specific initialization operations.
 * <li>runPreFeedFilePhases() Execute the set of preEventPhases defined
 * within the given feed file configuration document.
 * <li>initFeedFileAndFeedJob() - Update or create a new FeedFile record and
 * create a new FeedJob record for managing the feed life cycle for the given
 * feed file.
 * </ol>
 */
public abstract class AbstractFeedEngine {
  protected String feedId;
  protected String feedFileName;
  protected EngineContext context;
  protected Document feedDocument;
  protected Feed feed;
  protected FeedJob feedJob;

  protected FeedJobManager feedJobManager;

  private static Log logger = Log.getInstance(AbstractFeedEngine.class);

  public static boolean isNumeric(String buffer) {
    final String validChars = "0123456789";
    boolean isNumber = true;

    for (int i = 0; i < buffer.length() && isNumber; i++) {
      final char token = buffer.charAt(i);

      isNumber = validChars.indexOf(token) != -1;
    }

    return isNumber;
  }

  /**
   * Common error handler for the logging errors for the FeedErrorException is
   * thrown.
   *
   * @param feedId       identifies the feedId associated with the exception.
   * @param feedFileName identifies the name of the feed file associated with
   *                     the exception.
   * @param exception    contains the exception message.
   */
  protected void handleError(String feedId, String feedFileName, Exception exception) {
    final String message =
      "*** Unable to execute feed for feedId, " + feedId + ", feedFileName " + feedFileName + "." + "\n*** Exception: " + exception.getMessage();
    logger.error(message);
    exception.printStackTrace();
    throw new FeedErrorException(exception);
  }

  /**
   * Allows a concrete implementation of the AbstractFeedEngine to perform
   * its own specific initialization operations.
   */
  protected abstract void initialize();

  /**
   * A concrete implementation of the AbstractFeedEngine places it's core
   * lifecycle operations here.
   */
  public abstract void execute();

  /**
   * Loads all beans defined in configuration file in to the context in the
   * following sequence:
   * <ul>
   * <li>call BeanLoader to load beans
   * <li>put beans in context
   * <li>initialize loaders
   * <li>initialize services
   * <ul>
   */
  protected void loadBeans() {
    final BeanLoader loader = new BeanLoader(feedDocument.getRootElement(), context);
    final List<Object> beanList = loader.getBeans();
    final Map<String, Object> beanMap = loader.getBeanMap();

    putBeansInContext(beanMap);
    initLoaders(beanList);
    initServices(beanList);
    initPhases(beanList);
    initMainLifeCycleService(beanList);
  }

  private void putBeansInContext(Map<String, Object> beanMap) {
    for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
      context.putBean(entry.getKey(), entry.getValue());
    }
  }

  private void initLoaders(List<Object> beans) {
    for (Object bean : beans) {
      if (bean instanceof Loader) {
        ((Initializeable) bean).initialize();
      }
    }
  }

  private void initServices(List<Object> beans) {
    for (Object bean : beans) {
      if ((bean instanceof EngineService) && !(bean instanceof MainLifeCycleService)) {
        ((Initializeable) bean).initialize();
      }
    }
  }

  private void initPhases(List<Object> beans) {
    for (Object bean : beans) {
      if (bean instanceof Phase) {
        ((Initializeable) bean).initialize();
      }
    }
  }

  private void initMainLifeCycleService(List<Object> beans) {
    for (Object bean : beans) {
      if (bean instanceof MainLifeCycleService) {
        ((Initializeable) bean).initialize();
        return;
      }
    }

    throw new FeedErrorException("No service class that extends MainLifeCycleService class was found.");
  }

  /**
   * Store the feedId and filename as instance variables, place the filename
   * in the context and instantiate the feedJobManager.
   *
   * @param feedId       the feedId is used to attian information about the feed for
   *                     processing.
   * @param feedFileName specifies the unique file to process.
   */
  protected void initContext(String feedId, String feedFileName) {
    this.context = new EngineContext();
    this.feedId = feedId;

    if (feedFileName != null) {
      this.feedFileName = feedFileName;
      this.context.setFeedFileName(feedFileName);
    }
  }

  /**
   * Find the Feed record for the given feedId. Verify that the feedId is valid
   * and check it's validation rules to verify that the feed can be processed.
   */
  protected void initFeedRecord() {
    feed = feedJobManager.getFeed(feedId);

    checkAllowConcurrentRunState(feed);
    checkAllowFailedRunState(feed);
    checkActivationDate(feed);
    checkTermainationDate(feed);

    context.setFeed(feed);

    FeedFileHelper.createFeedDirectories(feed);
  }

  /**
   * Checks whether the given feed can run if another feed of its type is
   * currently in a processing state.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkAllowConcurrentRunState(Feed feed) {
    final FeedGroup feedGroup = feed.getFeedGroup();
    final Boolean allowConcurrentRuns = feedGroup != null ? feedGroup.isAllowConcurrentRuns() : feed.getAllowConcurrentRuns();

    if (!allowConcurrentRuns) {
      if (feedJobManager.anyFeedsInProcessingState(feed)) {
        final List<FeedFile> list = feedJobManager.findAllProcessingFeedsForFeedId(feedId);
        final FeedFile feedFile = list.get(0);
        final Integer feedFileId = feedFile.getFeedFileId();
        final String message =
          "Cannot run current feed for feed file, " + feedFileName + ", while feedFileId, " + feedFileId + " is in a processing state.";
        throw new FeedErrorException(message);
      }
    }
  }

  /**
   * Checks whether the given feed can run if another feed of its type is
   * currently in a failed state.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkAllowFailedRunState(Feed feed) {
    final FeedGroup feedGroup = feed.getFeedGroup();
    final Boolean allowFailedStateRuns = feedGroup != null ? feedGroup.isAllowFailedStateRuns() : feed.getAllowFailedStateRuns();

    if (!allowFailedStateRuns) {
      if (feedJobManager.anyFeedsInFailedState(feed)) {
        final List<FeedFile> list = feedJobManager.findAllFailedFeedsForFeedId(feedId);
        final FeedFile feedFile = list.get(0);
        final Integer feedFileId = feedFile.getFeedFileId();
        final String message =
          "Cannot run feed for feed file, " + feedFileName + ", while feedFileId, " + feedFileId + " is in a failed state.";
        throw new FeedErrorException(message);
      }
    }
  }

  /**
   * Checks to see if the given feed to run is befor its activation date.
   * If it is before its activation date an exception will be thrown.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkActivationDate(Feed feed) {
    if (!checkDate(feed.getActivationDate())) {
      final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
      final String message =
        "Feeds for feedId, " + feedId + ", cannot be activated for processing until " + sdf.format(feed.getActivationDate()) + ".";
      throw new FeedErrorException(message);
    }
  }

  /**
   * Checks to see if the given feed to run is after is given termination date.
   * If it is after its termination date an exception will be thrown.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkTermainationDate(Feed feed) {
    if (!checkDate(feed.getActivationDate())) {
      final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
      final String message =
        "Feeds for feedId, " + feedId + ", cannot be activated for processing after " + sdf.format(feed.getActivationDate()) + ".";
      throw new FeedErrorException(message);
    }
  }

  /**
   * Used to compare a given timestamp against the system date.
   *
   * @param timestamp the timestam to check.
   * @return true if the system date is greater than or equal to the given date.
   */
  private boolean checkDate(Timestamp timestamp) {
    final GregorianCalendar calendar1 = new GregorianCalendar();
    calendar1.setTime(new Timestamp(System.currentTimeMillis()));

    final GregorianCalendar calendar2 = new GregorianCalendar();
    calendar2.setTime(timestamp);

    final Date date1 = calendar1.getTime();
    final Date date2 = calendar2.getTime();
    final long time1 = date1.getTime();
    final long time2 = date2.getTime();

    return time1 >= time2;
  }

  /**
   * Determines if the feed is an inbound feed.
   *
   * @return true is feed direction is equal to 'inbound'.
   */
  protected Boolean isInboundFeed() {
    return feed.getFeedDirection().getFeedDirectionId().equals(FEED_DIRECTION.inbound.getValue());
  }

  /**
   * Define the complete location of the feed file by concatenating the
   * directory path defined in the Feed record with the given feed file
   * name. It will only do this fif the feed.feedDirection equals inbound.
   */
  protected void initFullPathAndFeedFileName() {
    if (isInboundFeed()) {
      final String directory = FEED_DIR.incoming.getValue();
      final String fullPath = FeedFileHelper.getFeedDirFullPath(context.getFeed()) + SLASH + directory + SLASH + feedFileName;

      context.setFullFeedFileName(fullPath);
    }
  }

  /**
   * Load the feed configuration document defined in the Feed record and place
   * a reference to the document in the engine context.
   */
  protected void initConfigFile() {
    final String configFileName = context.getFeed().getFeedDocument() + SLASH + CONFIG_FILE;

    feedDocument = FeedDocumentReader.parseDocument(configFileName);
    context.setFeedDocument(feedDocument);
  }

  /**
   * Execute the set of preEventPhases defined within the given
   * feed file configuration document.
   */
  protected void runPreFeedFilePhases() {
    final List<Phase> phases = context.getPreFeedFilePhases();

    if (phases != null) {
      for (Phase phase : phases) {
        context.setCurrentPhaseId(phase.getName());
        phase.execute();
      }
    }
  }

  /**
   * Update or create a new FeedFile record and create a new FeedJob record
   * for managing the feed life cycle for the given feed file.
   */
  protected void initFeedFileAndFeedJob() {
    feedJob = feedJobManager.createFeedJob(feedId, context);
    context.setFeedJob(feedJob);
  }

  /**
   * The main service is used to process a feed file through its defined phases.
   * The main service will also manage feed failures and shutdown the feed process.
   * The feed engine expects to find a main service defined within the feed file
   * configuration document.  If one is not found, the feed engine will throw
   * a FeedErrorException.  The main service that comes with the OSFE source code
   * is the StandardFeedLifeCycleService.
   */
  public void runMainService() {
    final ExecutionService service = context.getFeedLifeCycleService();

    if (service == null) {
      final String configFileName = feed.getFeedDocument();
      final String message =
        "A bean that extends FeedLifeCycleService is not defined in the configuration file, " + configFileName + ".";
      throw new FeedErrorException(message);
    }

    service.execute();
    service.shutdown();
  }
}
