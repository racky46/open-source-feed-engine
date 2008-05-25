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

import com.qagen.osfe.common.utils.RoundingHelper;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.FeedPhaseStatsService;
import com.qagen.osfe.dataAccess.vo.FeedPhaseStats;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * ShowPhaseStats echos, to the system console, the phase statistics for a
 * given feed file.
 */
public class ShowPhaseStats {
  public static final String FORMAT = "|%1$-40s|%2$-12s|%3$-10s|%4$-10s|\n";

  private final Integer feedFileId;
  private final FeedPhaseStatsService service;

  /**
   * Constuctor
   *
   * @param feedFileId identifies the feed file to echo phase statistics for.
   */
  public ShowPhaseStats(String feedFileId) {
    super();
    this.feedFileId = Integer.parseInt(feedFileId);
    this.service = (FeedPhaseStatsService) DataAccessContext.getBean(FeedPhaseStatsService.SERVICE_ID);
  }

  /**
   * This method must be called in order to perform the tasks of retrieving
   * the phases statistics for the given feed file and echo to the system console.
   */
  public void execute() {
    System.out.println("*** phase statistics for feedFileId: " + feedFileId + " ***");
    System.out.format(FORMAT, "phase_id", "avg_time_ms", "total_time", "iterations");
    final List<FeedPhaseStats> stats = service.findByFeedFileId(feedFileId);

    for (FeedPhaseStats stat : stats) {
      final String phaseId = stat.getPhaseId();
      final Double avgTime = RoundingHelper.round(stat.getAvgProcessingTime(), 4);
      final Long totalTime = stat.getTotalTimeInMs();
      final Integer count = stat.getIterationCount();

      System.out.format(FORMAT, phaseId, avgTime, totalTime, count);
    }
  }

  /**
   * arg[0] must contain the feedId.
   * <ul>
   * <li>Usage: ShowPhaseStats feedFileId
   * <li>Example: ShowPhaseStats 1000010
   * </ul>
   *
   * @param args reference to the command line arguments.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: ShowPhaseStats feedFileId");
      System.err.println("Example: ShowPhaseStats 1000010");
    } else {
      ShowPhaseStats job = new ShowPhaseStats(args[0]);
      job.execute();
    }
  }
}