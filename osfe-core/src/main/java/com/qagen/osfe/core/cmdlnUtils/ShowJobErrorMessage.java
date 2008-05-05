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

public class ShowJobErrorMessage {
  final FeedJobService service;
  final Integer feedJobId;

  public ShowJobErrorMessage(String feedJobId) {
    this.service = (FeedJobService) DataAccessContext.getBean(FeedJobService.SERVICE_ID);
    this.feedJobId = Integer.parseInt(feedJobId);
  }

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
