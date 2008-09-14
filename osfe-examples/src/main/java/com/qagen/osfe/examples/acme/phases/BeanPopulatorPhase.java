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


import com.qagen.osfe.core.phases.RowBeanPopulatorPhase;
import com.qagen.osfe.core.row.Row;
import com.qagen.osfe.examples.acme.row.DetailRow;

/**
 * Author: Hycel Taylor
 * <p/>
 * This BeanPopulatorPhase specifically populates the DetailRow object.
 */
public class BeanPopulatorPhase extends RowBeanPopulatorPhase {

  /**
   * Instantiate the DetaiRow object that will loaded with values from the
   * data within a given row of a given feed file.
   *
   * @return reference to a DetailRow object.
   */
  public Row getNewRow() {
    return new DetailRow();
  }
}
