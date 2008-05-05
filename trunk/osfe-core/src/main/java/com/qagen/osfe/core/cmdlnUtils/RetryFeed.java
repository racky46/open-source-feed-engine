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

import com.qagen.osfe.common.CommonConstants;
import com.qagen.osfe.core.FeedJobManager;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedJob;

/**
 * Author: Hycel Taylor
 * <p>
 * RetryFeed performs the following tasks.
 * <ul>
 * <li> Locates the failed feed for the given feed file id.
 * <li> Transitions the feedJob.state from failed to resolved.
 * <li> Transitions the feedFile.state from a failed to retry.
 * <li> Moves the physical feed file from /failed to /incoming.
 * </ul>
 * Usage: RetryFeed feedFileId<p>
 * Example: RetryFeed 100099
 */
public class RetryFeed {
  public Integer feedFileId;

  /**
   * Constructor
   * <p/>
   * When instantiating with this constructor, make sure to call the
   * setFeedFileId() method before calling the execute() method.
   */
  public RetryFeed() {
  }

  /**
   * Constructor
   *
   * @param feedFileId identifies the feedFile to retry.
   */
  public RetryFeed(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * Sets the feed file Id.  This method should be used by classes that need to
   * move a set of feed files from the failed state to the retry state.
   *
   * @param feedFileId specifies the feed file id to retry.
   */
  public void setFeedFileId(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * This method must be called in order to perform the tasks of moving the
   * given failed feed to the retry state.
   *
   * @return true if able to move to a retry state.
   */
  public Boolean execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);

    if (feedFile == null) {
      System.err.println("No feed file exists for feedFileId, " + feedFileId + ".");
    } else {
      final FeedJob feedJob = feedJobManager.getMostRecentFeedJob(feedFileId);
      return feedJobManager.moveToRetry(feedJob);
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
      System.err.println("Usage: RetryFeed feedFileId");
      System.err.println("Example: RetryFeed 10099");
    } else {
      try {
        final Integer feedFileId = Integer.parseInt(args[0]);
        final RetryFeed retryFeed = new RetryFeed(feedFileId);
        final Boolean success = retryFeed.execute();
        String message;

        if (success) {
          message = "The feed for feedId, " + feedFileId + ", has succefully been moved to a retry state.";
        } else {
          message = "Unable to move feedId, " + feedFileId + ", to a retry state.";
        }

        System.err.println(message);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
