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
package com.qagen.osfe.core.vo;

import java.util.Map;

public class Clazz {
  private String name;
  private String className;
  private Map<String, String> propertyMap;

  public Clazz(String name, String className, Map<String, String> propertyMap) {
    this.name = name;
    this.className = className;
    this.propertyMap = propertyMap;
  }

  public Clazz(String name, String className) {
    this(name, className, null);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Map<String, String> getPropertyMap() {
    return propertyMap;
  }

  public void setPropertyMap(Map<String, String> propertyMap) {
    this.propertyMap = propertyMap;
  }
}
