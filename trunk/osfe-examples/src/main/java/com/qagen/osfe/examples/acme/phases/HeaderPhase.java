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


import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.row.Row;
import com.qagen.osfe.core.row.RowValue;
import com.qagen.osfe.core.utils.BeanPopulator;
import com.qagen.osfe.examples.acme.row.HeaderRow;

import java.util.List;

public class HeaderPhase extends Phase {
  private Splitter headerSplitter;

  public void initialize() {
  }

  /**
   * Set the reference to the header splitter.
   *
   * <ul><li>Injection - required</li></ul>
   *
   * @param headerSplitter reference to the header splitter.
   */
  public void setHeaderSplitter(Splitter headerSplitter) {
    this.headerSplitter = headerSplitter;
  }

  public void execute() {
    final List<RowValue> rowValues = headerSplitter.getNextRow();
    final HeaderRow headerRow = new HeaderRow();

    BeanPopulator.populateBean(rowValues, headerRow);

    echoRowInfo(headerRow);
  }

  private void echoRowInfo(Row row) {
    final HeaderRow headerRow = (HeaderRow) row;
    System.out.println("Header: " + headerRow.getExamId() + "|" + headerRow.getCourseId());
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
