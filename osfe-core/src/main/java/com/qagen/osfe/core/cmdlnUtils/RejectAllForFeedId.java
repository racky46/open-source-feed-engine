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

import java.util.List;

public class RejectAllForFeedId {
  public enum PARAM {
    IGNORE_FILE_MOVE
  }

  private String feedId;
  private String ignoreFileMoveFlag;

  public RejectAllForFeedId(String feedId, String ignoreFileMoveFlag) {
    this.feedId = feedId;
    this.ignoreFileMoveFlag = ignoreFileMoveFlag;

    if (ignoreFileMoveFlag != null) {
      if (!ignoreFileMoveFlag.equals(PARAM.IGNORE_FILE_MOVE.name())) {
        final String message = "Second parameter does not equal " + PARAM.IGNORE_FILE_MOVE.name() + ".";
        throw new FeedErrorException(message);
      }
    }
  }

  public void execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final List<FeedFile> feedFiles = feedJobManager.findAllFailedFeedsForFeedId(feedId);
    final RejectFeed rejectFeed = new RejectFeed(ignoreFileMoveFlag);

    for (FeedFile feedFile : feedFiles) {
      rejectFeed.setFeedFileId(feedFile.getFeedFileId());
      rejectFeed.execute();
    }
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: RejectAllForFeedId feedId [IGNORE_FILE_MOVE]");
      System.err.println("Example: RejectAllForFeedId acme_qagen_warnings_request");
      System.err.println("Example: RejectAllForFeedId acme_qagen_warnings_request IGNORE_FILE_MOVE");
    } else {
      try {
        if (args.length == 1) {
          final RejectAllForFeedId service = new RejectAllForFeedId(args[0], null);
          service.execute();
        } else {
          final RejectAllForFeedId service = new RejectAllForFeedId(args[0], args[1]);
          service.execute();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
