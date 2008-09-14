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

import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.row.RowValue;
import org.apache.commons.beanutils.WrapDynaBean;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author: Gregg Bolinger, Hycel Taylor
 * <p/>
 */
public class BeanPopulator {
  public static final String ATTRIBUTE_FORMAT = "format";


  public static Object getValueAsType(String name, String value, String type, String format) {
    if ((value != null) && (value.trim().length() == 0)) {
      return null;
    }

    if (type.equals(AttributeType.String.name())) {
      return value;
    }

    if (type.equals(AttributeType.Integer.name())) {
      return Integer.parseInt(value);
    }

    if (type.equals(AttributeType.Float.name())) {
      return Float.parseFloat(value);
    }

    if (type.equals(AttributeType.Double.name())) {
      return Double.parseDouble(value);
    }

    if (type.equals(AttributeType.Boolean.name())) {
      return Boolean.parseBoolean(value);
    }

    if (type.equals(AttributeType.Date.name())) {
      return getDate(name, value, format);
    }

    if (type.equals(AttributeType.Time.name())) {
      return getTime(name, value, format);
    }

    if (type.equals(AttributeType.Long.name())) {
      return Long.parseLong(value);
    }

    if (type.equals(AttributeType.Timestamp.name())) {
      return getTimestamp(name, value, format);
    }

    if (type.equals(AttributeType.Object.name())) {
      return Timestamp.valueOf(value);
    }

    final String message =
      "The value type, " + type + " is not a defined type.  " +
        "You may need to extend the RowParser class and override the method, getValueAsType().";
    throw new FeedErrorException(message);
  }

  private static Timestamp getTimestamp(String name, String value, String format) {
    try {
      if ((format == null) || (format.trim().length() == 0)) {
        final String message = "Column, " + name + " is define as a Timestamp and must the attribute, format, defined.";
        throw new FeedErrorException(message);
      }

      final SimpleDateFormat formatter = new SimpleDateFormat(format);
      return new Timestamp(formatter.parse(value).getTime());

    } catch (ParseException e) {
      throw new FeedErrorException(e);
    }
  }

  private static Time getTime(String name, String value, String format) {
    try {
      if ((format == null) || (format.trim().length() == 0)) {
        final String message = "Column, " + name + " is define as a Time and must the attribute, format, defined.";
        throw new FeedErrorException(message);
      }

      final SimpleDateFormat formatter = new SimpleDateFormat(format);
      return new Time(formatter.parse(value).getTime());

    } catch (ParseException e) {
      throw new FeedErrorException(e);
    }
  }

  private static Date getDate(String name, String value, String format) {
    try {
      if ((format == null) || (format.trim().length() == 0)) {
        final String message = "Column, " + name + " is define as a Date and must the attribute, format, defined.";
        throw new FeedErrorException(message);
      }

      final SimpleDateFormat formatter = new SimpleDateFormat(format);
      return new Date(formatter.parse(value).getTime());

    } catch (ParseException e) {
      throw new FeedErrorException(e);
    }
  }

  public static void populateBean(List<RowValue> rowValues, Object bean) {
    final WrapDynaBean wrapDynaBean = new WrapDynaBean(bean);

    for (RowValue rowValue : rowValues) {
      final String name = rowValue.getDescription().getName();
      final String value = rowValue.getValue();
      final String type = rowValue.getDescription().getType();
      final String format = rowValue.getDescription().getFormat();
      wrapDynaBean.set(name, getValueAsType(name, value, type, format));
    }
  }

}
