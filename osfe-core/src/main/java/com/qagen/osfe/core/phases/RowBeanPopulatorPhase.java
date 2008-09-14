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
package com.qagen.osfe.core.phases;

import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.row.Row;
import com.qagen.osfe.core.row.RowValue;
import com.qagen.osfe.core.utils.BeanPopulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * RowBeanPopulatorPhase is abstract and performs the work of matching row values
 * with the appropriate bean value. This class is specifically desinged to
 * perform these operations on detail rows of a feed file.  Thus, this class will
 * specifically call context.getDetailSplitter(), in order to reference a detail
 * spltter only.
 */
public abstract class RowBeanPopulatorPhase extends Phase {
  private Splitter detailSplitter;
  private Integer batchSize;
  private List<Row> rows;

  /**
   * Set the reference to the detail splitter.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param detailSplitter reference to the detail splitter.
   */
  public void setDetailSplitter(Splitter detailSplitter) {
    this.detailSplitter = detailSplitter;
  }

  /**
   * Reference the detailSplitter and batchSize from the context.
   */
  public void initialize() {
    batchSize = context.getBatchSize();
    rows = new ArrayList<Row>();
  }

  /**
   * Create a list of RowValue objects by traversing the list of raw data objects,
   * populate beans and store those beans in RowValue objects.  Then, place
   * the list of RowValue objects in the engine context.
   */
  public void execute() {
    rows.clear();

    Integer batchCounter = 0;
    while (detailSplitter.hasNextRow() && (batchCounter++ < batchSize)) {
      final List<RowValue> rowValues = detailSplitter.getNextRow();
      final Row detailRow = getNewRow();

      BeanPopulator.populateBean(rowValues, detailRow);
      rows.add(detailRow);
    }

    context.setRows(rows);
  }

  /**
   * Instantiate the specific type of bean that is to be loaded with values
   * from the data within a given row of a given feed file.
   *
   * @return reference to a bean.
   */
  public abstract Row getNewRow();

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public void shutdown() {
    // No resources to close...
  }
}
