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
 * Author : Hycel Taylor
 * <p/>
 * The RowParser interface defines the contract for concrete row parsers.  The
 * behavior of a row parser is to parse data for a specific row type and place
 * the raw values in to a list of RowValue objects.
 */
public interface RowParser {

  /**
   * Performs the task of parsing the values of a row and creating a list of
   * row value objects.
   *
   * @param row the object that contains a row of data to be parsed in to a
   *            list of row values.
   * @return    list of RowValue objects.
   */
  public List<RowValue> parseRow(Object row);
}
