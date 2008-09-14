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
package com.qagen.osfe.examples.acme.phases;

import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.ProcessPhase;
import com.qagen.osfe.core.row.Row;
import com.qagen.osfe.examples.acme.AcmeConstants;
import com.qagen.osfe.examples.acme.GradingStatistics;
import com.qagen.osfe.examples.acme.row.DetailRow;

/**
 * Author: Hycel Taylor
 * <p/>
 * Determine the grade the score should receive and keeps track
 * of the total score and count of stundents. The feed will be
 * invalidated if any score is not within the range of 0 - 100.
 */
public class GradingPhase extends ProcessPhase {
  private Boolean echoDetail = false;
  private Integer delayInSeconds = 0;

  private GradingStatistics stats;

  /**
   * Determines if log information is echoed to the console.
   *
   * <ul><li>Injection - optional</li></ul>
   *
   * @param echoDetail default is false.
   */
  public void setEchoDetail(Boolean echoDetail) {
    this.echoDetail = echoDetail;
  }

  /**
   * Delays the processing of each row by the given numbers of seconds.
   *
   * <ul><li>Injection - optional</li></ul>
   *
   * @param delayInSeconds default is 0
   */
  public void setDelayInSeconds(Integer delayInSeconds) {
    this.delayInSeconds = delayInSeconds;
  }

  public void initialize() {
    stats = new GradingStatistics();
    context.put(AcmeConstants.stats.name(), stats);
  }

  public void execute() {
    singlePhaseLoop();
  }

  /**
   * Determine grade for score.
   *
   * @param row contains the data extracted from a raw feed file row.
   */
  public void processRow(Row row) {
    DetailRow detailRow = (DetailRow) row;
    final Integer score = detailRow.getScore();

    if (delayInSeconds > 0) {
      try { Thread.sleep(delayInSeconds * 1000); } catch (InterruptedException e) {}
    }

    if ((score < 0) || (score > 100)) {

      context.setRejectedRowNumber();
      context.setErrorCode("GRAD100");
      context.setErrorMessage("Illegal score: " + score);
      throw new FeedErrorException(context.getErrorMessage());
    }

    stats.setTotalScore(stats.getTotalScore() + score);
    stats.setExamCount(stats.getExamCount() + 1);

    determineGrade(detailRow);

    if (echoDetail) {
      echoRowInfo(row);
    }
  }

  private void determineGrade(DetailRow row) {

    if (row.getScore() == null) {
      System.out.println("");
      return;
    }

    final Integer score = row.getScore();

    if (score <= 65) {
      row.setGrade("F");
      return;
    }

    if (score <= 69) {
      row.setGrade("D");
      return;
    }

    if (score <= 79) {
      row.setGrade("C");
      return;
    }

    if (score <= 89) {
      row.setGrade("B");
      return;
    }

    row.setGrade("A");
  }

  private void echoRowInfo(Row row) {
    final String delimiter = " | ";
    final DetailRow detailRow = (DetailRow) row;
    final StringBuilder builder = new StringBuilder("Student: ");

    builder.append(detailRow.getStudentId()).append(delimiter);
    builder.append(detailRow.getScore()).append(delimiter);
    builder.append(detailRow.getGrade()).append(delimiter);
    builder.append(detailRow.getLastName()).append(delimiter);
    builder.append(detailRow.getFirstName()).append(delimiter);

    System.out.println(builder.toString());
  }

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public void shutdown() {
    // No resources to close...
  }
}
