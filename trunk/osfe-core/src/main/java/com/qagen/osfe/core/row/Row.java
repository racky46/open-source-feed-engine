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
package com.qagen.osfe.core.row;

/**
 * Author : Hycel Taylor
 * <p/>
 * The Row abstract class defines the contract for all concrete Row implementations.
 * Row represents a row of the data to OSFE.  No matter the complexity of the
 * data structure within a given feed file, it must be parsed and represented as
 * a Row.  Concrete classes that extend Row are simply Java Beans that represent
 * a specific data structure type for a given feed file row and have Row behavior.
 */
public abstract class Row {
  private Boolean enable = true;

  protected Row() {
  }

  /**
   * Specifies whether the given Row is available for processing.  This method
   * is often used in filter phases to filter out rows that should be ignored
   * when passed to the next set of phases.
   *
   * @return true if the row be processed by subsequent phases, false if the row
   *         should be ignored by subsequent phases.
   */
  public Boolean getEnable() {
    return enable;
  }

  /**
   * Used to specify whether a row should be enabled or disabled.  Disabling a
   * row means that the row can be ignored as it passes through subsequent phases.
   *
   * @param enable set to false to disable the row. Default is true.
   */
  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

}
