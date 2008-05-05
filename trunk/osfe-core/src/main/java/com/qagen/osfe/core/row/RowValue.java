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

import com.qagen.osfe.core.column.ColumnDescription;

/**
 * Author : Hycel Taylor
 * <p/>
 * The RowValue class contains a raw value and a ColumnDescrition object
 * that describes the nominclature of the given raw value.
 */
public class RowValue {
  // Description of the given column within a given RowDescription object.
  private final ColumnDescription description;
  // The actual raw value.
  private final String value;

  /**
   * Constructor
   *
   * @param description describes the given column within a given RowDescription object.
   * @param value the raw value being described.
   */
  public RowValue(ColumnDescription description, String value) {
    this.description = description;
    this.value = value;
  }

  /**
   * Returns the nominclature of the column.
   *
   * @return ColumnDescription object.
   */
  public ColumnDescription getDescription() {
    return description;
  }

  /**
   * Returns the raw value.
   *
   * @return value as a string.
   */
  public String getValue() {
    return value;
  }
}
