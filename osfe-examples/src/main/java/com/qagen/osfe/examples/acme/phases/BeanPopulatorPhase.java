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


import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.row.RowValue;
import com.qagen.osfe.core.utils.BeanPopulator;
import com.qagen.osfe.examples.acme.row.DetailRow;

import java.util.ArrayList;
import java.util.List;

public class BeanPopulatorPhase extends Phase {
  private Splitter splitter;
  private Integer batchSize;
  private List<DetailRow> rows;

  public BeanPopulatorPhase(EngineContext context, String name) {
    super(context, name);
  }

  public void initialize() {
    splitter = context.getDetailSplitter();
    batchSize = context.getBatchSize();
    rows = new ArrayList<DetailRow>();
  }

  public void execute() {
    rows.clear();

    Integer batchCounter = 0;
    while (splitter.hasNextRow() && (batchCounter++ < batchSize)) {
      final List<RowValue> rowValues = splitter.getNextRow();
      final DetailRow detailRow = new DetailRow();

      BeanPopulator.populateBean(rowValues, detailRow);
      rows.add(detailRow);
    }

    context.setRows(rows);
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
