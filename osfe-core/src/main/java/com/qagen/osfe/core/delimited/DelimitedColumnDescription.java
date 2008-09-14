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

import com.qagen.osfe.common.utils.DomReader;
import com.qagen.osfe.core.column.ColumnDescription;
import org.dom4j.Element;

/**
 * Author: Hycel Taylor
 * <p/>
 * This classe extends ColumnDescription and addes specific attributes for
 * describing a delimited column.
 */
public class DelimitedColumnDescription extends ColumnDescription {
  private static enum ATTRIBUTE {
    description
  }

  protected final String description;

  /**
   * Constructor
   *
   * @param element reference to element that contains attributes for the given
   *                column description.
   */
  public DelimitedColumnDescription(Element element) {
    super(element);
    description = DomReader.getValue(element, ATTRIBUTE.description.name());
  }

  /**
   * Retrieves a text description of the column.
   *
   * @return a text description of the column.
   */
  public String getDescription() {
    return description;
  }
}
