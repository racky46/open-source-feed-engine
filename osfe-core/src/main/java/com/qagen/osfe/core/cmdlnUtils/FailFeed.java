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

import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.FeedJobManager;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedJob;

/**
 * Author: Hycel Taylor
 * <p>
 * FailFeed performs the following tasks.
 * <ul>
 * <li> Locates the processing feed for the given feed file id.
 * <li> Transitions the feedJob.state from active to failed.
 * <li> Transitions the feedFile.state from a processing to failed.
 * <li> Moves the physical feed file from /workarea to /failed.
 * </ul>
 * Usage: FailFeed feedFileId<p>
 * Example: FailFeed 100099
 */
public class FailFeed {
  public Integer feedFileId;

  /**
   * Constructor
   *
   * When instantiating with this constructor, make sure to call the
   * setFeedFileId() method before calling the execute() method.
   */
  public FailFeed() {
  }

  /**
   * Constructor
   *
   * @param feedFileId identifies the feedFile to restart.
   */
  public FailFeed(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * Sets the feed file Id.  This method should be used by classes that need to
   * move a set of feed files from the processing state to the failed state.
   *
   * @param feedFileId specifies the feed file id to fail.
   */
  public void setFeedFileId(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * This method must be called in order to perform the tasks of moving the
   * given processing feed to the failed state.
   *
   * @return true if able to move to a failed state.
   */
  public Boolean execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);

    if (feedFile == null) {
      throw new FeedErrorException("No feed file exists for feedFileId, " + feedFileId + ".");
    } else {
      final FeedJob feedJob = feedJobManager.getMostRecentFeedJob(feedFileId);
      return feedJobManager.moveToFailed(feedJob);
    }
  }

  /**
   * arg[0] must contain the feedFileId.
   *
   * @param args reference to the command line arguments.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: FailFeed feedFileId");
      System.err.println("Example: FailFeed 10099");
    } else {
      try {
        final Integer feedFileId = Integer.parseInt(args[0]);
        final FailFeed failFeed = new FailFeed(feedFileId);
        final Boolean success = failFeed.execute();

        String message;
        if (success) {
          message = "The feed for feedId, " + feedFileId + ", has succefully been moved to a failed state.";
        } else {
          message = "Unable to move feedId, " + feedFileId + ", to a failed state.";
        }

        System.err.println(message);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
