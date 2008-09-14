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
package com.qagen.osfe.core.utils;

import com.qagen.osfe.common.utils.NumberFormatter;
import com.qagen.osfe.core.FeedErrorException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class ValueToString {

  public static String valueToString(Object bean, String fieldId, String type, String pattern) {
    try {
      final Object value = PropertyUtils.getSimpleProperty(bean, fieldId);

      switch (AttributeType.valueOf(type)) {
        case Integer:
        case Long:
        case Float:
        case Double:
          return formatNumber(value, pattern);
        case Time:
        case Date:
        case Timestamp:
          return formatTimestamp(value, pattern);
      }

      return value.toString();

    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    } catch (NoSuchMethodException e) {
      throw new FeedErrorException(e);
    }
  }

  private static String formatNumber(Object value, String pattern) {
    return (pattern != null) ? NumberFormatter.formatNumber(value, pattern) : value.toString();
  }

  private static String formatTimestamp(Object value, String pattern) {
    if (pattern == null) {
      return value.toString();
    }

    final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(value);
  }
}
