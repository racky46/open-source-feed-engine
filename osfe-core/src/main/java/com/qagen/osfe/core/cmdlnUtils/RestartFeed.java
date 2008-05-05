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
package com.qagen.osfe.core.cmdlnUtils;

import com.qagen.osfe.core.FeedJobManager;
import com.qagen.osfe.dataAccess.vo.FeedFile;

/**
 * Author: Hycel Taylor
 * <p>
 * RestartFeed performs the following tasks.
 * <ul>
 * <li> Locates the feed file from the given feed file id.
 * <li> Checks the the feed file is in a processing state.
 * Otherwise throws FeedErrorException.
 * <li> Transitions the feedJob.state from active to failed.
 * <li> Transitions the feedFile.state from a processing to failed.
 * <li> Moves the physical feed file from /workarea to /failed.
 * <li> Transitions the feedJob.state from failed to resolved.
 * <li> Transitions the feedFile.state from a failed to retry.
 * <li> Moves the physical feed file from /failed to /incoming.
 * </ul>
 * Usage: RestartFeed feedFileId<p>
 * Example: RestartFeed 100099
 */
public class RestartFeed {
  public Integer feedFileId;

  /**
   * Constructor
   * <p/>
   * When instantiating with this constructor, make sure to call the
   * setFeedFileId() method before calling the execute() method.
   */
  public RestartFeed() {
  }

  /**
   * Constructor
   *
   * @param feedFileId identifies the feedFile to restart.
   */
  public RestartFeed(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * * Sets the feed file Id.  This method should be used by classes that need to
   * move a set of feed files from the processing state to the retry state.
   *
   * @param feedFileId specifies the feed file id to retry.
   */
  public void setFeedFileId(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * This method must be called in order to perform the tasks of moving the
   * given failed feed to the restart state.
   *
   * @return true if able to restart feedFile.
   */
  public Boolean execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);

    if (feedFile == null) {
      System.err.println("No feed file exists for feedFileId, " + feedFileId + ".");
    } else {

      return feedJobManager.restartFeedFile(feedFileId);
    }

    return false;
  }

  /**
   * arg[0] must contain the feedFileId.
   *
   * @param args reference to the command line arguments.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: RestartFeed feedFileId");
      System.err.println("Example: RestartFeed 10099");
    } else {
      try {
        final Integer feedFileId = Integer.parseInt(args[0]);
        final RestartFeed restartFeed = new RestartFeed(feedFileId);
        final Boolean success = restartFeed.execute();
        String message;

        if (success) {
          message = "The feed for feedId, " + feedFileId + ", has succefully been moved to a restart state.";
        } else {
          message = "Unable to restart feedId, " + feedFileId + ".";
        }

        System.err.println(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}