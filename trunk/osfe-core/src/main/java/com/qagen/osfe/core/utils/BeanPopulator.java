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
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.row.RowValue;
import org.apache.commons.beanutils.WrapDynaBean;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Author: Gregg Bolinger, Hycel Taylor
 * <p/>
 */
public class BeanPopulator {
  public static final String ATTRIBUTE_FORMAT = "format";

  public static enum TYPE {
    Date,
    Time,
    Long,
    Float,
    String,
    Double,
    Integer,
    Boolean,
    Timestamp,
    Object
  }

  public static Object getValueAsType(String name, String value, String type, String format) {
    if ((value != null) && (value.trim().length() == 0)) {
      return null;
    }

    if (type.equals(TYPE.String.name())) {
      return value;
    }

    if (type.equals(TYPE.Integer.name())) {
      return Integer.parseInt(value);
    }

    if (type.equals(TYPE.Float.name())) {
      return Float.parseFloat(value);
    }

    if (type.equals(TYPE.Double.name())) {
      return Double.parseDouble(value);
    }

    if (type.equals(TYPE.Boolean.name())) {
      return Boolean.parseBoolean(value);
    }

    if (type.equals(TYPE.Date.name())) {
      return Date.valueOf(value);
    }

    if (type.equals(TYPE.Time.name())) {
      return Time.valueOf(value);
    }

    if (type.equals(TYPE.Long.name())) {
      return Long.parseLong(value);
    }

    if (type.equals(TYPE.Timestamp.name())) {
      return getTimestamp(name, value, format);
    }

    if (type.equals(TYPE.Object.name())) {
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
        final String message = "Column, " + name + " is define as a Timestamp and must the format attribute defined.";
        throw new FeedErrorException(message);
      }

      final SimpleDateFormat formatter = new SimpleDateFormat(format);
      return new Timestamp(formatter.parse(value).getTime());

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

  /**
   * populates a Object using setter methods via reflection for a given set of
   * properties.
   *
   * @param object     the object to introspect on.
   * @param properties contains the list of properties to set on the given object.
   * @throws FeedErrorException
   */
  public static void populatePhaseProperties(final Object object, Map<String, String> properties) throws FeedErrorException {
    if (properties == null) {
      return;
    }

    try {
      final BeanInfo info = Introspector.getBeanInfo(object.getClass());

      for (final String key : properties.keySet()) {
        final Method writeMethod = getDesiredWriteMethod(key, info);

        // since its a simple write method assume one parameter
        if (writeMethod != null) {
          final Class<?> type = writeMethod.getParameterTypes()[0];
          writeMethod.invoke(object, getValueAsType(null, properties.get(key), type.getSimpleName(), null));
        } else {
          final String name = object.getClass().getSimpleName();
          throw new FeedErrorException("Property, " + key + ", has no setter method defined for object, " + name + ".");
        }
      }
    } catch (IntrospectionException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (IllegalArgumentException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Gets the desired write method based on the field name and BeanInfo
   *
   * @param field field to match to.
   * @param info  contains the property description.
   * @return java.lang.reflect.Method
   */
  private static Method getDesiredWriteMethod(String field, BeanInfo info) {
    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
      if (pd.getName().equals(field)) {
        return pd.getWriteMethod();
      }
    }
    return null;
  }
}
