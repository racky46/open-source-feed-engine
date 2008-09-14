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

import com.qagen.osfe.core.utils.ValueToString;
import com.qagen.osfe.core.vo.DelimitedColumn;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This is a utility class ...
 */
public class DelimitedRowBuilder {

  /**
   * @param bean
   * @param columns
   * @param delimiter
   * @return
   */
  public static String rowBuilder(Object bean, List<DelimitedColumn> columns, String delimiter) {
    final StringBuilder builder = new StringBuilder();
    Integer counter = 0;

    for (DelimitedColumn column : columns) {
      final String name = column.getName();
      final String type = column.getType();
      final String format = column.getFormat();

      final String value = ValueToString.valueToString(bean, name, type, format);
      builder.append(value);

      if (++counter < columns.size()) {
        builder.append(delimiter);
      }
    }

    return builder.toString();
  }

}
