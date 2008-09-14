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

import static com.qagen.osfe.common.FeedConstants.FEED_JOB_STATE;
import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * ShowActiveJobs retrieves the set of feed jobs that are in an active state
 * and echos the results to the console.
 */
public class ShowFailedJobs extends ShowJobsBase {

  /**
   * Constructor
   */
  public ShowFailedJobs() {
    super();
  }

  /**
   * This method must be called in order to perform the tasks of retrieving
   * the set of feed jobs in a failed state and echoed to the console.
   */
  public void execute() {
    final List<FeedJob> failedJobs = feedJobService.findByFeedJobStateId(FEED_JOB_STATE.failed.getValue());
    showJobs(failedJobs);
  }

  /**
   * @param args no argurments necessary.
   */
  public static void main(String[] args) {
    final ShowFailedJobs job = new ShowFailedJobs();
    job.execute();
  }
}
