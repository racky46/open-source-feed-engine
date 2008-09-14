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

/**
 * Author: Hycel Taylor
 * <p/>
 * The behavior of a row parser is to parse data for a specific row type and place
 * the raw values in to a list of RowValue objects. This particular class parses
 * delimited column data.
 */
public class DelimitedRowParser implements RowParser {
  public static enum DELEMITER_TYPE {
    Tab,
    Windows,
    Unix,
    Mac
  }

  private List<ColumnDescription> columnDescriptions;
  private String delimiter;

  /**
   * Constructor
   *
   * @param rowDescription contains the column descriptions.
   * @param delimiter      defines the value used to delimited the columns.
   */
  public DelimitedRowParser(RowDescription rowDescription, String delimiter) {
    this.delimiter = checkDelimiterType(delimiter);
    this.columnDescriptions = rowDescription.getColumns();
  }

  /**
   * Determines if the type of delimiter is specifically a tab,
   * Unix, Windows or Mac. If it is one of these, then it returns
   * that appriate character delimiter.  Otherwise it returns
   * the character(s) defined as the delimiter.
   *
   * @param delimiter defines the type of delimiter.
   * @return the character delimiter.
   */
  private String checkDelimiterType(String delimiter) {
    if (delimiter.equals(DELEMITER_TYPE.Tab.name())) {
      return "\t";
    } else if (delimiter.equals(DELEMITER_TYPE.Windows.name())) {
      return "\r\n";
    } else if (delimiter.equals(DELEMITER_TYPE.Unix.name())) {
      return "\n";
    } else if (delimiter.equals(DELEMITER_TYPE.Mac.name())) {
      return "\r";
    } else {
      return delimiter;
    }
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

    int limit = 1000;
    if (string.length() > limit) {
      limit = string.length();
    }

    final String escapedDelimiter = "\\" + delimiter;
    final String[] array = string.split(escapedDelimiter, limit);
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
