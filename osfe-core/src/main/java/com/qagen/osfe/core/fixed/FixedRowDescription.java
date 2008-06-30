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

import com.qagen.osfe.core.row.RowDescription;
import com.qagen.osfe.core.column.ColumnDescription;
import com.qagen.osfe.common.utils.DomReader;
import org.dom4j.Element;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * The FixedRowDescription class extends RowDescrition and adds new methods in
 * order to handle special attributes the describe a fixed row.
 */
public class FixedRowDescription extends RowDescription {
  private static enum ATTRIBUTE {
    length
  }

  private Integer rowLength;

  /**
   * Constructor
   *
   * @param element            the element to parse in order to retrieve the
   *                           common attributes of a row description (name,
   *                           rowCount, linesToSkip).
   * @param columnDescriptions reference to the list of columnDescription
   *                           objects.
   */
  public FixedRowDescription(Element element, List<ColumnDescription> columnDescriptions) {
    super(element, columnDescriptions);
    rowLength = DomReader.getRequiredIntValue(element, ATTRIBUTE.length.name());
  }

  /**
   * Retrieves the values that defines the length of the row.
   *
   * @return length of the row.
   */
  public Integer getRowLength() {
    return rowLength;
  }
}
