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

import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.FeedJobService;
import com.qagen.osfe.dataAccess.vo.FeedJob;

/**
 * Author: Hycel Taylor
 * <p/>
 * ShowJobHistory retrieves error information of a given feed job
 * and echos the results to the system console.
 */
public class ShowJobErrorMessage {
  final FeedJobService service;
  final Integer feedJobId;

  /**
   * Constructor
   *
   * @param feedJobId the feed job to retrieve error results for.
   */
  public ShowJobErrorMessage(String feedJobId) {
    this.service = (FeedJobService) DataAccessContext.getBean(FeedJobService.SERVICE_ID);
    this.feedJobId = Integer.parseInt(feedJobId);
  }

  /**
   * This method must be called in order to perform the tasks of retrieving
   * the error information for the given feed job and echoed to the console.
   */
  public void execute() {
    final FeedJob feedJob = service.findByPrimaryId(feedJobId);
    final String message = feedJob.getFailureMessage();
    final String code = feedJob.getFailureCode();

    if (message == null) {
      System.err.println("No error message found for jobId " + feedJobId + ".");
    } else {
      System.err.println(" Feed JobId: " + feedJobId);
      if (code != null)
        System.err.println(" Error Code: " + code);
      System.err.println(" Error message: " + message);
    }
  }

  /**
   * arg[0] must contain the feedJobId.
   * <ul>
   * <li>Usage: ShowJobErrorMessage feedJobId
   * <li>Example: ShowJobErrorMessage 100001
   * </ul>
   *
   * @param args reference to the command line arguments.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: ShowJobErrorMessage feedJobId");
      System.err.println("Example: ShowJobErrorMessage 100001");
    } else {
      final ShowJobErrorMessage jobErrorMessage = new ShowJobErrorMessage(args[0]);
      jobErrorMessage.execute();
    }
  }
}
