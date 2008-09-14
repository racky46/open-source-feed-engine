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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Aug 7, 2008
 * Time: 11:27:27 AM
 */
public class BeanInjector {

  public static void injectRequiredBean(Object bean, String field, Object value) {
    try {
      final BeanInfo info = Introspector.getBeanInfo(bean.getClass());
      final Method writeMethod = getDesiredWriteMethod(field, info);

      if (writeMethod != null) {
        writeMethod.invoke(bean, value);
      } else {
        final String name = bean.getClass().getSimpleName();
        throw new FeedErrorException("The setter method for property, " + field + ", was not found in bean, " + name + ".");
      }
    } catch (IntrospectionException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
  }

  public static void injectNonRequiredBean(Object bean, String field, Object value) {
    try {
      final BeanInfo info = Introspector.getBeanInfo(bean.getClass());
      final Method writeMethod = getDesiredWriteMethod(field, info);

      if (writeMethod != null) {
        writeMethod.invoke(bean, value);
      }
    } catch (IntrospectionException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
  }

  public static void injectRequiredValue(Object bean, String field, String value, String type, String format) {
    try {
      final BeanInfo info = Introspector.getBeanInfo(bean.getClass());
      final Method writeMethod = getDesiredWriteMethod(field, info);

      if (writeMethod != null) {
        final Object object = BeanPopulator.getValueAsType(field, value, type, format);
        writeMethod.invoke(bean, object);
      } else {
        final String name = bean.getClass().getSimpleName();
        throw new FeedErrorException("The setter method for property, " + field + ", was not found in bean, " + name + ".");
      }
    } catch (IntrospectionException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
  }

  public static void injectValue(Object bean, String field, String value, String type, String format) {
    try {
      final BeanInfo info = Introspector.getBeanInfo(bean.getClass());
      final Method writeMethod = getDesiredWriteMethod(field, info);

      if (writeMethod != null) {
        final Object object = BeanPopulator.getValueAsType(field, value, type, format);
        writeMethod.invoke(bean, object);
      }
    } catch (IntrospectionException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
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
  public static Method getDesiredWriteMethod(String field, BeanInfo info) {
    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
      if (pd.getName().equals(field)) {
        return pd.getWriteMethod();
      }
    }
    return null;
  }

}
