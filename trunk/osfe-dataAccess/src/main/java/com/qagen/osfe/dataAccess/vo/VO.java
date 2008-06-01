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
package com.qagen.osfe.dataAccess.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This is the base value object class from which all other value objects
 * extend.
 */
public class VO implements Serializable {
  public static final String BAR = " | ";

  public String toString(List<String> list) {
    final StringBuilder builder = new StringBuilder();
    final int size = list.size();
    int count = 0;

    for (String string : list) {
      builder.append(string);
      if (++count < size) {
        builder.append(BAR);
      }
    }
    return builder.toString();
  }
}
