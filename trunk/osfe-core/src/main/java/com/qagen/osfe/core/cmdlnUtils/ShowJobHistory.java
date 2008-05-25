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

/**
 * Author: Hycel Taylor
 * <p>
 * ShowJobHistory retrieves the set of feed jobs for the given feed file
 * and echos the results to the system console.
 */
public class ShowJobHistory extends ShowJobsBase {
  private final Integer feedFileId;

  /**
   * Constructor
   *
   * @param feedFileId identifies the feedFile to show feed job history.
   */
  public ShowJobHistory(String feedFileId) {
    super();
    this.feedFileId = Integer.parseInt(feedFileId);
  }

  /**
   * This method must be called in order to perform the tasks of retrieving
   * the set of feed jobs for the given feed file and echoed to the console.
   */
  public void execute() {
    final List<FeedJob> failedJobs = feedJobService.findByFeedFileId(feedFileId);
    showFeedJobsForFeedFile(failedJobs);
  }

  /**
   * arg[0] must contain the feedFileId.
   * <ul>
   * <li>Usage: ShowJobHistory feedFileId
   * <li>Example: ShowJobHistory 1000010
   * </ul>
   *
   * @param args reference to the command line arguments.
   */
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
