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

import com.qagen.osfe.examples.acme.AcmeConstants;
import com.qagen.osfe.examples.acme.GradingStatistics;
import com.qagen.osfe.examples.acme.row.DetailRow;
import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.row.Row;

import java.util.List;

public class GradingPhase extends Phase {
  private Boolean echoDetail;
  private Integer delay;
  private GradingStatistics stats;

  public GradingPhase(EngineContext context, String name) {
    super(context, name);

    stats = new GradingStatistics();
    context.put(AcmeConstants.stats.name(), stats);

    delay = Integer.parseInt((String) context.get(AcmeConstants.delay.name()));
  }

  public void setEchoDetail(Boolean echoDetail) {
    this.echoDetail = echoDetail;
  }

  public void initialize() {
  }

  @SuppressWarnings("unchecked")
  public void execute() {
    if (delay > 0) {
      try { Thread.sleep(1000 * delay); } catch (InterruptedException e) {}
    }

    final List<DetailRow> rows = (List<DetailRow>) context.get(AcmeConstants.detailData.name());

    context.resentCurrentRowIndex();
    for (DetailRow row : rows) {
      context.incrementCurrentRowIndex();

      final Integer score = row.getScore();

      if ((score < 0) || (score > 100)) {
        context.setRejectedRowNumber();
        context.setErrorCode("GRAD100");
        context.setErrorMessage("Illegal score: " + score);
        throw new FeedErrorException(context.getErrorMessage());
      }

      stats.setTotalScore(stats.getTotalScore() + score);
      stats.setExamCount(stats.getExamCount() + 1);

      determineGrade(row);

      if (echoDetail) {
        echoRowInfo(row);
      }

      context.incrementProcessedRowCount();
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
