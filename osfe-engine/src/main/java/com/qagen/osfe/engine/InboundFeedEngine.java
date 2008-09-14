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
import com.qagen.osfe.dataAccess.vo.FeedFile;

public class InboundFeedEngine extends AbstractFeedEngine implements Runnable {

  /**
   * Constructor
   * <p/>
   * Normally used to process a feed for that has not been processed before.
   * This constructor can also be used to process a feed that has alread been
   * processed; however, it's easier to use the constructor which only requires
   * the feedFileId.
   *
   * @param feedId       the feedId is used to attian information about the feed for
   *                     processing.
   * @param feedFileName specifies the unique file to process.
   */
  public InboundFeedEngine(String feedId, String feedFileName) {
    try {
      feedJobManager = new FeedJobManager();
      initFeedProcess(feedId, feedFileName);
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
  public InboundFeedEngine(Integer feedFileId) {
    try {
      feedJobManager = new FeedJobManager();

      final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);
      final String feedId = feedFile.getFeed().getFeedId();
      final String feedFileName = feedFile.getFeedFileName();

      context.setSequenceNumber(feedFile.getSequenceNumber());

      initFeedProcess(feedId, feedFileName);
    } catch (Exception e) {
      handleError(feedId, feedFileName, e);
    }
  }

  /**
   * Perform the initialization sequence to prepare a feed for processing.
   *
   * @param feedId       identifies the feedId associated with the exception.
   * @param feedFileName identifies the name of the feed file associated with
   *                     the exception.
   */
  protected void initFeedProcess(String feedId, String feedFileName) {
    initContext(feedId, feedFileName);
    initFeedRecord();
    initFullPathAndFeedFileName();
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
    if (args.length < 2) {
      System.err.println("Usage: InboundFeedEngine feedId, feedFileName");
      System.err.println("Example: InboundFeedEngine acme.qagen.testd.request acme_qagen_test_request_20080101.txt\n");
      System.err.println("Usage: InboundFeedEngine feedFileId");
      System.err.println("Example: InboundFeedEngine 100001");
      System.exit(-1);
    }

    if (args.length == 2) {
      final InboundFeedEngine engine = new InboundFeedEngine(args[0], args[1]);
      engine.execute();
    } else {
      final InboundFeedEngine engine = new InboundFeedEngine(Integer.parseInt(args[0]));
      engine.execute();
    }
  }
}
