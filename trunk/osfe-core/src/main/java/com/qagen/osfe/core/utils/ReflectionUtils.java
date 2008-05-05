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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Author: Gregg Bolinger
 * <p>
 *
 */
public class ReflectionUtils {

  /**
   * Using reflection fields all the fields for a particluar class
   * including all super classes
   *
   * @param clazz
   * @return Class
   */
  public static Collection<Field> getFields(Class<?> clazz) {
    Map<String, Field> fields = new HashMap<String, Field>();
    while (clazz != null) {
      for (Field field : clazz.getDeclaredFields()) {
        if (!fields.containsKey(field.getName())) {
          fields.put(field.getName(), field);
        }
      }
      clazz = clazz.getSuperclass();
    }
    return fields.values();
  }

  /**
   * Using reflection gets all the mehtods of a class including
   * all super classes
   * 
   * @param clazz
   * @return
   */
  public static Collection<Method> getMethods(Class<?> clazz) {
    Collection<Method> found = new ArrayList<Method>();

    while (clazz != null) {
      for (Method m1 : clazz.getDeclaredMethods()) {
        boolean overridden = false;

        for (Method m2 : found) {
          if (m2.getName().equals(m1.getName()) &&
            Arrays.deepEquals(m1.getParameterTypes(), m2.getParameterTypes())) {
            overridden = true;
            break;
          }
        }

        if (!overridden) found.add(m1);
      }

      clazz = clazz.getSuperclass();
    }

    return found;
  }

}
