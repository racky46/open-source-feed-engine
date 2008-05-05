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

public class HeapStats {

  /**
   * Get maximum size of heap in bytes. The heap cannot grow beyond this size.
   * Any attempt will result in an OutOfMemoryException.
   */
  public static Long getMaxHeapSize() {
    return Runtime.getRuntime().maxMemory();
  }

  /**
   * Get current size of heap in bytes
   */
  public static Long getHeapSize() {
    return Runtime.getRuntime().totalMemory();
  }

  /**
   * Get amount of free memory within the heap in bytes. This size will increase
   * after garbage collection and decrease as new objects are created.
   */
  public static Long getFreeHeapSize() {
    return Runtime.getRuntime().freeMemory();
  }

}
