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

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * FailAllForFeedId retrieves the set all of the feed files in a processing
 * state for the given feedFileId.  It then iterates over each one and calls
 * FailFeed.java which performs the actual task of failing an individual feed
 * file.
 */
public class FailAllForFeedId {
  private String feedId;

  /**
   * @param feedId identifies the set of processing feeds to fail.
   */
  public FailAllForFeedId(String feedId) {
    this.feedId = feedId;
  }

  /**
   * This method must be called in order to perform the tasks of retrieving
   * the set of feed files in a processing state and moving them to the failed state.
   */
  public void execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final List<FeedFile> feedFiles = feedJobManager.findAllProcessingFeedsForFeedId(feedId);
    final FailFeed failFeed = new FailFeed();

    for (FeedFile feedFile : feedFiles) {
      failFeed.setFeedFileId(feedFile.getFeedFileId());
      failFeed.execute();
    }
  }

  /**
   * arg[0] must contain the feedId.
   * <ul>
   * <li>Usage: Usage: FailAllForFeedId feedId
   * <li>Example: FailAllForFeedId wsi_lm_warnings_request
   * </ul>
   *
   * @param args reference to the command line arguments.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: FailAllForFeedId feedId");
      System.err.println("Example: FailAllForFeedId wsi_lm_warnings_request");
    } else {
      try {
        FailAllForFeedId service = new FailAllForFeedId(args[0]);
        service.execute();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
