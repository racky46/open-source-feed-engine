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
package com.qagen.osfe.core;

/**
 * Author: Hycel Taylor
 * <p/>
 * The interface defines special behavior for footer classes.
 */
public interface FooterSplitter extends Splitter {

  /**
   * Many feed files have footer rows that specify the total number of rows
   * in a given feed file. This methood should perform the actions that
   * determine that calculation.
   *
   * @return the count of the number of rows in a feed file.
   */
  public Integer getTotalRowCount();

  /**
   * The total number of rows in a feed file is calculated as the
   * total number rows in the detail, minus the total number of rows
   * in the header and the footer. This method should return the total
   * number of rows that should be subtracted the calculation of the
   * total row count.
   *
   * @return the total numbers of rows that should be subtracted from
   *         the calculation of the total row count.
   */
  public Integer getMinusRowCount();

}
