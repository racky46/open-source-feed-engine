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

public class FailAllForFeedId {
  private String feedId;

  public FailAllForFeedId(String feedId) {
    this.feedId = feedId;
  }

  public void execute() {
    final FeedJobManager feedJobManager = new FeedJobManager();
    final List<FeedFile> feedFiles = feedJobManager.findAllProcessingFeedsForFeedId(feedId);
    final FailFeed failFeed = new FailFeed();

    for (FeedFile feedFile : feedFiles) {
      failFeed.setFeedFileId(feedFile.getFeedFileId());
      failFeed.execute();
    }
  }

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
