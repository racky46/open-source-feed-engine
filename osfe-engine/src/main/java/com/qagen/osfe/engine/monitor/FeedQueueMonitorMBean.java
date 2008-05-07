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

public interface FeedQueueMonitorMBean {

  /**
   * Creates and adds a FeedMappedQueue object to a queue specified by the
   * feed fileFileId.
   *
   * @param feedId       identifies the feed.
   * @param feedFileName identifies the name and location of the feed file to process.
   */
  public void add(String feedId, String feedFileName);

  /**
   * Notifies monitor that a feed of a given queueId type is no longer
   * being processed by the feed engine.
   *
   * @param queueId identifies the queue that is no longer active.
   */
  public void notifyQueue(String queueId);

  /**
   * Places a queue, for the given queueId, in a hold or non hold state.
   * When a the condition is true, the given queue can still receive feeds
   * for processing but may not release any feeds for processing.
   *
   * @param queueId    identifies the queue to place in a hold or non hold state.
   * @param condition set to true to place all queues on hold.
   */
  public void changeOnHoldState(String queueId, boolean condition);

  /**
   * Places all queues in a hold or non hold state.  When a the condition is
   * true, all queues can still receive feeds for processing but may not
   * release any feeds for processing.
   *
   * @param condition set to true to place all queues on hold.
   */
  public void changeOnHoldState(boolean condition);

  /**
   * Empties a given queue in memory and in the database cache.
   *
   * @param queueId identifies the queue to empty.
   */
  public void clearQueue(String queueId);

  /**
   * Empties all queues in memory and in the database cache.
   */
  public void clearQueues();

  /**
   * Places the given feed at the front of it mapped queue for processing.
   *
   * @param feedId       defines the feed.
   * @param feedFileName the name of the feed file to process.
   */
  public void launchFeed(String feedId, String feedFileName);

  /**
   * Places a given feed which is in a Failed state into a Retry state and
   * places the given feed at the front of its mapped queue for processing
   *
   * @param feedFileId - identifies the feed to fail.
   */
  public void retryFeed(Integer feedFileId);

  /**
   * Places all feed queues on hold.<p>
   * Waits for the completion of all actively running queues and sends a notification that all feeds have completed.
   */
  public void shutDownFeeds();

  /**
   * Places all feed queues on hold.<p>
   * Waits for the completion of all actively running queues and sends a notification that all feeds have completed.<p>
   * Sends a notification that it is shutting down.<p>
   * Executes a System.exit(0);
   */
  public void shutdown();
}
