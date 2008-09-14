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

import com.qagen.osfe.common.utils.DomReader;
import com.qagen.osfe.core.column.ColumnDescription;
import org.dom4j.Element;

import java.util.List;

/**
 * Author : Hycel Taylor
 * <p/>
 * The RowDescription class contains the minimum attributes necessary to
 * describe row.
 */
public class RowDescription {
  // Defines the common attributes of a row desciption element.
  private static enum ATTRIBUTE {
    name("name"),
    rowCount("rowCount"),
    linesToSkip("linesToSkip");

    private String value;

    ATTRIBUTE(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // Uniquely identifies the given row description.
  private final String name;
  // Defines the number or row that should exist within the feed file.
  private final Integer rowCount;
  // Defines the number of lines to skip after the header.
  private final Integer linesToSkip;
  // Contains the list of ColumnDescription objects.
  private final List<ColumnDescription> columnDescriptions;

  /**
   * Constructor
   *
   * @param element            the element to parse in order to retrieve the
   *                           common attributes of a row description (name,
   *                           rowCount, linesToSkip).
   * @param columnDescriptions reference to the list of columnDescription
   *                           objects.
   */
  public RowDescription(Element element, List<ColumnDescription> columnDescriptions) {
    name = DomReader.getRequiredValue(element, ATTRIBUTE.name.getValue());
    rowCount = DomReader.getIntValue(element, ATTRIBUTE.rowCount.getValue());
    linesToSkip = DomReader.getIntValue(element, ATTRIBUTE.linesToSkip.getValue());
    this.columnDescriptions = columnDescriptions;
  }

  /**
   * The name is used to identify the type of rowDescription.
   *
   * @return name of the row.
   */
  public String getName() {
    return name;
  }

  /**
   * Defines the number of rows that exist within a given feed file for a given
   * type of rowDescription. The rowCount is often necessary when describing the
   * header and footer rowsDescritions in order to calculate the number of detail
   * rows that exist within a given feed file.
   *
   * @return the number of rows that exist for a given row type.
   */
  public Integer getRowCount() {
    return rowCount;
  }

  /**
   * Defines the number of lines to skip after the header rows and before the
   * start of the detail rows.
   *
   * @return the number of lines to skip.
   */
  public Integer getLinesToSkip() {
    return linesToSkip;
  }

  /**
   * Defines the list ColumnsDescription objects that describe each column
   * of the given row description.
   *
   * @return list of ColumnDescription objects.
   */
  public List<ColumnDescription> getColumns() {
    return columnDescriptions;
  }

}
