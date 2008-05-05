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
 * RejectFeed performs the following tasks.
 * <ul>
 * <li> Locates the failed feed for the given feed file id.
 * <li> Transitions the feedJob.state from failed to rejected.
 * <li> Transitions the feedFile.state from a failed to resolved.
 * <li> Moves the physical feed file from /failed to /rejected.
 * </ul>
 * Usage: RejectFeed feedFileId [IGNORE_FILE_MOVE]<p>
 * Example: RejectFeed 100009<p>
 * Example: RejectFeed 100009 IGNORE_FILE_MOVE
 */
public class RejectFeed {
  public enum PARAM {
    IGNORE_FILE_MOVE
  }

  private Integer feedFileId;
  private Boolean ignoreFileMove = false;

  /**
   * Constructor
   * <p/>
   * When instantiating with this constructor, make sure to call the
   * setFeedFileId() method before calling the execute() method.
   */
  public RejectFeed() {
  }

  /**
   * Constructor
   *
   * @param feedFileId identifies the feedFile to reject.
   */
  public RejectFeed(Integer feedFileId) {
    this(feedFileId, null);
  }

  /**
   * Constructor
   *
   * @param feedFileId         feedFileId identifies the feedFile to reject.
   * @param ignoreFileMoveFlag specifies whether the task to move the physical
   *                           file from the /failed directory to /rejected directory
   *                           should be executed.
   *                           It may be the case that the physical file is not
   *                           in the /failed directory.  Thus, the task of moving
   *                           the physical file should not be done.
   */
  public RejectFeed(Integer feedFileId, String ignoreFileMoveFlag) {
    this.feedFileId = feedFileId;
    checkIgnoreFileMoveFlag(ignoreFileMoveFlag);
  }

  /**
   * Constructor
   *
   * @param ignoreFileMoveFlag specifies whether the task to move the physical
   *                           file from the /failed directory to /rejected directory
   *                           should be executed.
   *                           It may be the case that the physical file is not
   *                           in the /failed directory.  Thus, the task of moving
   *                           the physical file should not be done.
   */
  public RejectFeed(String ignoreFileMoveFlag) {
    checkIgnoreFileMoveFlag(ignoreFileMoveFlag);
  }

  private void checkIgnoreFileMoveFlag(String ignoreFileMoveFlag) {
    if (ignoreFileMoveFlag != null) {
      if (ignoreFileMoveFlag.equals(PARAM.IGNORE_FILE_MOVE.name())) {
        ignoreFileMove = true;
      } else {
        final String message = "Second parameter does not equal " + PARAM.IGNORE_FILE_MOVE.name() + ".";
        throw new FeedErrorException(message);
      }
    }
  }

  /**
   * Sets the feed file Id.  This method should be used by classes that need to
   * move a set of feed files from the failed state to the rejected state.
   *
   * @param feedFileId specifies the feed file id to reject.
   */
  public void setFeedFileId(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  /**
   * This method must be called in order to perform the tasks of moving the
   * given failed feed to the rejected state.
   *
   * @return true if albe to move to a rejected state.
   */
  public Boolean execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);

    if (feedFile == null) {
      System.err.println("No feed file exists for feedFileId, " + feedFileId + ".");
    } else {
      final FeedJob feedJob = feedJobManager.getMostRecentFeedJob(feedFileId);

      if (ignoreFileMove) {
        return feedJobManager.moveToRejectedNoFileMove(feedJob);
      } else {
        return feedJobManager.moveToRejected(feedJob);
      }
    }

    return false;
  }

  /**
   * <ul>
   * <li>arg[0] must contain the feedFileId.
   * <li>arg[1] may contain the constant IGNORE_FILE_MOVE.
   * </ul>
   *
   * @param args reference to the command line arguments.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: RejectFeed feedFileId [IGNORE_FILE_MOVE]");
      System.err.println("Example: RejectFeed 100009");
      System.err.println("Example: RejectFeed 100009 IGNORE_FILE_MOVE");
    } else {
      try {
        final Integer feedFileId = Integer.parseInt(args[0]);
        Boolean success;

        if (args.length == 1) {
          final RejectFeed rejectFeed = new RejectFeed(feedFileId);
          success = rejectFeed.execute();
        } else {
          final RejectFeed rejectFeed = new RejectFeed(feedFileId, args[1]);
          success = rejectFeed.execute();
        }

        String message;
        if (success) {
          message = "The feed for feedId, " + feedFileId + ", has succefully been moved to a rejected state.";
        } else {
          message = "Unable to move feedId, " + feedFileId + ", to a rejected state.";
        }
        System.err.println(message);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
