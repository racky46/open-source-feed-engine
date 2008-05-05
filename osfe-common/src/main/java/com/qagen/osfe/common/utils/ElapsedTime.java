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

import static com.qagen.osfe.common.CommonConstants.COLON;

public class ElapsedTime {

  public static String getElapsedTime(Long start) {
    // Get elapsed time in milliseconds
    long millis = System.currentTimeMillis() - start;

    // Get elapsed time in seconds
    int sec = (int) millis / 1000;

    // Get elapsed time in minutes
    int min = (int) millis / (60 * 1000);

    // Get elapsed time in hours
    int hour = (int) millis / (60 * 60 * 1000);

    // Get elapsed time in days
    int day = (int) millis / (24 * 60 * 60 * 1000);

    final StringBuilder builder = new StringBuilder();

    if (day > 0) {
      builder.append(day).append(" day(s), ");
    }

    builder.append(hour).append(COLON);
    builder.append(min).append(COLON);
    builder.append(sec).append(COLON);
    builder.append(millis);

    return builder.toString();
  }
}
