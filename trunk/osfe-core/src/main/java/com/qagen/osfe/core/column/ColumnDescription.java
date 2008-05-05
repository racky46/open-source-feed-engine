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
package com.qagen.osfe.core.column;

import com.qagen.osfe.common.utils.DomReader;
import org.dom4j.Element;

public class ColumnDescription {
  public static enum ATTRIBUTE {
    name,
    type,
    format
  }

  protected final String name;
  protected final String type;
  protected final String format;

  protected ColumnDescription(Element element) {
    name = DomReader.getRequiredValue(element, ColumnDescription.ATTRIBUTE.name.name());
    type = DomReader.getRequiredValue(element, ColumnDescription.ATTRIBUTE.type.name());
    format = DomReader.getValue(element, ColumnDescription.ATTRIBUTE.format.name());
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getFormat() {
    return format;
  }
}
