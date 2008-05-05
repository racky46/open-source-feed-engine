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

public class DelimitedColumn extends ColumnDescription {
  public static enum ATTRIBUTE {
    description
  }

  protected final String description;

  public DelimitedColumn(Element element) {
    super(element);
    description = DomReader.getValue(element, ATTRIBUTE.description.name());
  }
}
