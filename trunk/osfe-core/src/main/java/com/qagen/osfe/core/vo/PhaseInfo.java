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

/**
 * Author: Hycel Taylor
 * <p>
 * This class contains the necessary information for loading a Phase at runtime.
 */
public class PhaseInfo {
  private String name;
  private Boolean enable;
  private String className;
  private Map<String, String> propertyMap;

  /**
   * Constructor
   *
   * @param name         should uniquely identify a phase within a set of phases.
   * @param enable       specifies whether the phases should be used or ignored.
   * @param className    specifies the name of the Phase extended class to load at runtime.
   * @param propertyMap  specifies a list of properties specific to the given phase.
   */
  public PhaseInfo(String name, Boolean enable, String className, Map<String, String> propertyMap) {
    this.name = name;
    this.enable = enable;
    this.className = className;
    this.propertyMap = propertyMap;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
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
