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

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * User: Gregg Bolinger
 * Date: Feb 29, 2008
 * Time: 1:51:56 PM
 * <p/>
 * Class with a couple of common utils primarily used for Log.java but
 * wanted to keep them seperate
 */
public class StringUtil {

  /**
   * Combine a bunch of objects into a single String.  Array contents get converted
   * nicely
   *
   * @param messageParts
   * @return
   */
  public static String combineParts(Object... messageParts) {
    StringBuilder builder = new StringBuilder(128);
    for (Object part : messageParts) {
      if (part != null && part.getClass().isArray()) {
        builder.append(Arrays.toString(asObjectArray(part)));
      } else {
        builder.append(part);
      }
    }
    return builder.toString();
  }

  /**
   * Converts an Object reference that is known to be an array into an Object[]
   * If the array is assignable to Object[] the array passed in is simply cast and
   * returned.  Otherwise a new Object[] of equal size is constsructed and the elements
   * are inserted into the new array before being returned.
   *
   * @param in
   * @return
   */
  private static Object[] asObjectArray(Object in) {
    if (in == null || !in.getClass().isArray()) {
      throw new IllegalArgumentException("Parameter to asObjectArray must be a non-null array");
    } else if (in instanceof Object[]) {
      return (Object[]) in;
    } else {
      final int length = Array.getLength(in);
      Object[] out = new Object[length];
      for (int index = 0; index < length; index++) {
        out[index] = Array.get(in, index);
      }
      return out;
    }
  }
}
