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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * ShowJobsBase is an abstract class and must be extended.  It contains
 * methods for echoing formatted data to the system console.
 */
public abstract class ShowJobsBase {
  public static final String FORMAT_I = "|%1$-10s|%2$-10s|%3$-40s|%4$-22s|%5$-60s|\n";
  public static final String FORMAT_II = "|%1$-10s|%2$-10s|%3$-40s|%4$-15s|%5$-22s|%6$-22s|\n";
  public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";

  final FeedJobService feedJobService;
  private SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);

  /**
   * Constructor
   */
  public ShowJobsBase() {
    feedJobService = (FeedJobService) DataAccessContext.getBean(FeedJobService.SERVICE_ID);
  }

  /**
   * Echos formatted data to the system console for the follwoing attributes:
   * <ul>
   * <li>FeedJobId
   * <li>FeedFileId
   * <li>FeedId
   * <li>StartTime
   * <li>FeedFileName
   * </ul>
   *
   * @param feedJobs reference to the set of feedJob objects to echo information for.
   */
  public void showJobs(List<FeedJob> feedJobs) {
    System.out.format(FORMAT_I, "FeedJobId", "FeedFileId", "FeedId", "StartTime", "FeedFileName");

    for (FeedJob feedJob : feedJobs) {
      final Integer feedJobId = feedJob.getFeedJobId();
      final Integer feedFileId = feedJob.getFeedFile().getFeedFileId();
      final String feedId = feedJob.getFeedFile().getFeed().getFeedId();
      final String feedFileName = feedJob.getFeedFile().getFeedFileName();
      final Timestamp timeStamp = feedJob.getProcessingStart();
      final String dateTime = dateFormat.format(timeStamp);

      System.out.format(FORMAT_I, feedJobId, feedFileId, feedId, dateTime, feedFileName);
    }
  }

  /**
   * Echos formatted data to the system console for the follwoing attributes:
   * <ul>
   * <li>FeedJobId
   * <li>FeedFileId
   * <li>FeedId
   * <li>State
   * <li>StartTime
   * <li>EndTime
   * </ul>
   *
   * @param feedJobs reference to the set of feedJob objects to echo information for.
   */
  public void showFeedJobsForFeedFile(List<FeedJob> feedJobs) {
    System.out.format(FORMAT_II, "FeedJobId", "FeedFileId", "FeedId", "State", "StartTime", "EndTime");

    for (FeedJob feedJob : feedJobs) {
      final Integer feedJobId = feedJob.getFeedJobId();
      final Integer feedFileId = feedJob.getFeedFile().getFeedFileId();
      final String feedId = feedJob.getFeedFile().getFeed().getFeedId();
      final String feedJobState = feedJob.getFeedJobState().getFeedJobStateId();
      final Timestamp startTime = feedJob.getProcessingStart();
      final Timestamp endTime = feedJob.getProcessingEnd();
      final String dateTime = dateFormat.format(startTime);

      System.out.format(FORMAT_II, feedJobId, feedFileId, feedId, feedJobState, dateTime, endTime);
    }
  }
}
