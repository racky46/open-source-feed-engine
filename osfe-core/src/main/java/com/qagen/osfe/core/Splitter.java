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

import com.qagen.osfe.core.row.RowValue;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This interface defines the contract for all splitter classes.  A splitter
 * is responsible for accessing the rows of data from a specific block of
 * data within a given feed file.
 * <p/>
 * For example, most feed files are broken up into three blocks:
 * <ul>
 * <li>header - access to the header rows of a feed file
 * <li>detail - access to the detail rows of a feed file (normally in batches of 1,000 - 10,000 rows).
 * <li>footer - access to the footer rows of a feed file
 * </ul>
 * Splitters hide all of the complexities of retrieving data from a feed file.
 * The contract methods below ensure a clean and consistent representation of
 * the feed file to all classes needing data from a given feed file irrespective
 * of the original format of the given feed file.
 */
public interface Splitter {

  /**
   * Retrieves a list or RowValue objects for the given feed file.
   *
   * @return reference to a list of RowValue objects.
   */
  public List<RowValue> getNextRow();

  /**
   * Determines if there is another row to retrieve from the feed file.
   *
   * @return true if more rows. false if end of file has been reached.
   */
  public Boolean hasNextRow();

  /**
   * Sometimes certain operations need to be executed prior to the first row
   * being retrieved but after initialization.
   */
  public void prePhaseExecute();
}
