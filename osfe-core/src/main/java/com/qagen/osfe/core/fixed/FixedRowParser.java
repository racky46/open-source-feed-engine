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
package com.qagen.osfe.core.fixed;

import com.qagen.osfe.core.RowParser;
import com.qagen.osfe.core.column.ColumnDescription;
import com.qagen.osfe.core.row.RowValue;
import com.qagen.osfe.core.row.RowDescription;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: Hycel Taylor
 * <p/>
 * The behavior of a row parser is to parse data for a specific row type and place
 * the raw values in to a list of RowValue objects.  This particular class parses
 * fixed column data.
 */
public class FixedRowParser implements RowParser {
  private List<ColumnDescription> columnDescriptions;

  /**
   * Constructor
   *
   * @param rowDescription contains the column descriptions.
   */
  public FixedRowParser(RowDescription rowDescription) {
    this.columnDescriptions = rowDescription.getColumns();
  }

  /**
   * Performs the task of parsing the values of a row and creating a list of
   * row value objects.
   *
   * @param row the object that contains a row of data to be parsed in to a
   *            list of row values.
   * @return list of RowValue objects.
   */
  public List<RowValue> parseRow(Object row) {
    final String string = (String) row;
    final List<RowValue> rowValues = new ArrayList<RowValue>();

    for (ColumnDescription description : columnDescriptions) {
      final FixedColumn fixedColumn = (FixedColumn) description;
      final Integer start = fixedColumn.getStart() - 1;
      final Integer length = fixedColumn.getLength();
      final String value = string.substring(start, start + length).trim();
      final RowValue rowValue = new RowValue(description, value);

      rowValues.add(rowValue);
    }

    return rowValues;
  }
}
