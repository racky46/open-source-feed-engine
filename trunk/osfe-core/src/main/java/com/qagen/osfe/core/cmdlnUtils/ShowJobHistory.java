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

import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.util.List;

public class ShowJobHistory extends ShowJobsBase {
  private final Integer feedFileId;

  public ShowJobHistory(String feedFileId) {
    super();
    this.feedFileId = Integer.parseInt(feedFileId);
  }

  public void execute() {
    final List<FeedJob> failedJobs = feedJobService.findByFeedFileId(feedFileId);
    showFeedJobsForFeedFile(failedJobs);
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: ShowJobHistory feedFileId");
      System.err.println("Example: ShowJobHistory 1000010");
    } else {
      ShowJobHistory job = new ShowJobHistory(args[0]);
      job.execute();
    }
  }
}
