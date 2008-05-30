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
package com.qagen.osfe.core.delimited;

import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.RowParser;
import com.qagen.osfe.core.column.ColumnDescription;
import com.qagen.osfe.core.row.RowDescription;
import com.qagen.osfe.core.row.RowValue;

import java.util.ArrayList;
import java.util.List;

public class DelimitedRowParser implements RowParser {
  public static enum DELEMITER_TYPE {
    Tab,
    Windows,
    Unix
  }

  private List<ColumnDescription> columnDescriptions;
  private String delimiter;

  public DelimitedRowParser(RowDescription rowDescription, String delimiter) {
    this.delimiter = checkDelimiterType(delimiter);
    this.columnDescriptions = rowDescription.getColumns();
  }

  private String checkDelimiterType(String delimiter) {
    if (delimiter.equals(DELEMITER_TYPE.Tab.name())) {
      return "\t";
    } else if (delimiter.equals(DELEMITER_TYPE.Windows.name())) {
      return "\r\n";
    } else if (delimiter.equals(DELEMITER_TYPE.Unix.name())) {
      return "\n";
    } else {
      return delimiter;
    }
  }

  public List<RowValue> parseRow(Object row) {
    final String string = (String) row;
    final String[] array = string.split(delimiter, 1000);
    final List<RowValue> rowValues = new ArrayList<RowValue>();

    if (array.length > columnDescriptions.size()) {
      final String message =
        "\nThe delimited column count of " + array.length +
          ", does not match the feed description column count of " + columnDescriptions.size() +
          "\n Row with the error: " + string + ".";
      throw new FeedErrorException(message);
    }

    int index = 0;
    for (String value : array) {
      final ColumnDescription description = columnDescriptions.get(index++);
      final RowValue rowValue = new RowValue(description, value);

      rowValues.add(rowValue);
    }

    return rowValues;
  }
}
