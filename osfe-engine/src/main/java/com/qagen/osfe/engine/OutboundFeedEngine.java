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

import com.qagen.osfe.core.FeedJobManager;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.Feed;

public class OutboundFeedEngine extends AbstractFeedEngine implements Runnable {

  /**
   * Constructor
   * <p/>
   *
   * @param feedId specifies the feedId for outbound feed processing.
   */
  public OutboundFeedEngine(String feedId) {
    try {
      feedJobManager = new FeedJobManager();
      initFeedProcess(feedId, null);
    } catch (Exception e) {
      handleError(feedId, feedFileName, e);
    }
  }

  /**
   * Constructor
   * <p/>
   * This constructor should be used to rerun a feedFile that is in a retry state.
   *
   * @param feedFileId identifies a file that have been previously processed.
   */
  public OutboundFeedEngine(Integer feedFileId) {
    try {
      feedJobManager = new FeedJobManager();

      final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);
      final Feed feed = feedFile.getFeed();

      if (!feedJobManager.isOutboundFeed(feed)) {
        final String message = "FeedId, " + feedFileId + ", is not an outbound feed.";
        throw new FeedErrorException(message);
      }

      final String feedId = feed.getFeedId();
      final String feedFileName = feedFile.getFeedFileName();

      initFeedProcess(feedId, feedFileName);

      context.setSequenceNumber(feedFile.getSequenceNumber());
    } catch (Exception e) {
      handleError(feedId, feedFileName, e);
    }
  }

  /**
   * Perform the initialization sequence to prepare a feed for processing.
   *
   * @param feedId identifies the feedId associated with the exception.
   *               the exception.
   */
  protected void initFeedProcess(String feedId, String feedFileName) {
    initContext(feedId, feedFileName);
    initFeedRecord();
    initConfigFile();
    loadBeans();
    initialize();
    runPreFeedFilePhases();
    initFeedFileAndFeedJob();
  }

  protected void initialize() {
  }

  public void execute() {
    runMainService();
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used
   * to create a thread, starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p/>
   * The general contract of the method <code>run</code> is that it may
   * take any action whatsoever.
   *
   * @see Thread#run()
   */
  public void run() {
    execute();
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: OutboundFeedEngine feedId");
      System.err.println("Example: OutboundFeedEngine acme.qagen.testd.request");
      System.exit(-1);
    }

    final String value = args[0];

    if (isNumeric(value)) {
      final OutboundFeedEngine engine = new OutboundFeedEngine(Integer.parseInt(value));
      engine.execute();
    } else {
      final OutboundFeedEngine engine = new OutboundFeedEngine(value);
      engine.execute();
    }

  }
}
