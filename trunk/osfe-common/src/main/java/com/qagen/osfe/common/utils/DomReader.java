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
package com.qagen.osfe.common.utils;

import com.qagen.osfe.common.CommonException;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class DomReader {

  public static String getValue(Element element, String attributeName) {
    final Attribute attribute = element.attribute(attributeName);

    if (attribute == null) {
      return null;
    }

    return attribute.getValue();
  }

  public static String getRequiredValue(Element element, String attributeName) {
    final String value = getValue(element, attributeName);

    if (value == null) {
      throw new CommonException("The attribute, " + attributeName + ", is a required attribute and may not be null.");
    }

    if (value.trim().length() == 0) {
      throw new CommonException("The attribute, " + attributeName + ", is a required attribute and may not be empty.");
    }

    return value;
  }

  public static Integer getIntValue(Element element, String attributeName) {
    final String value = getValue(element, attributeName);

    if (value != null) {
      return Integer.parseInt(value);
    }

    return null;
  }

  public static Integer getRequiredIntValue(Element element, String attributeName) {
    final Integer value = getIntValue(element, attributeName);

    if (value == null) {
      throw new CommonException("The attribute, " + attributeName + ", is a required attribute and may not be null.");
    }

    return value;
  }

  public static Long getLongValue(Element element, String attributeName) {
    final String value = getValue(element, attributeName);

    if (value != null) {
      return Long.parseLong(value);
    }

    return null;
  }

  public static Long getRequiredLongValue(Element element, String attributeName) {
    final Long value = getLongValue(element, attributeName);

    if (value == null) {
      throw new CommonException("The attribute, " + attributeName + ", is a required attribute and may not be null.");
    }

    return value;
  }

  public static Float getFloatValue(Element element, String attributeName) {
    final String value = getValue(element, attributeName);

    if (value != null) {
      return Float.parseFloat(value);
    }

    return null;
  }

  public static Float getRequiredFloatValue(Element element, String attributeName) {
    final Float value = getFloatValue(element, attributeName);

    if (value == null) {
      throw new CommonException("The attribute, " + attributeName + ", is a required attribute and may not be null.");
    }

    return value;
  }

  public static Boolean getBooleanValue(Element element, String attributeName) {
    final String value = getValue(element, attributeName);

    if (value != null) {
      return Boolean.parseBoolean(value);
    }

    return null;
  }

  public static Boolean getRequiredBooleanValue(Element element, String attributeName) {
    final Boolean value = getBooleanValue(element, attributeName);

    if (value == null) {
      throw new CommonException("The attribute, " + attributeName + ", is a required attribute and may not be null.");
    }

    return value;
  }

}
