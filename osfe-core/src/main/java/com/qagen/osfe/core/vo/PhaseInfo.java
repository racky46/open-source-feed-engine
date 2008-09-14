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

/**
 * Author: Hycel Taylor
 * <p/>
 * This class contains the necessary information for loading a Phase at runtime.
 */
public class PhaseInfo {
  private String ref;
  private Boolean enable;

  public String getRef() {
    return ref;
  }

  /**
   * Constructor
   *
   * @param ref    should uniquely identify a phase within a set of phases.
   * @param enable specifies whether the phases should be used or ignored.
   */
  public PhaseInfo(String ref, Boolean enable) {
    this.ref = ref;
    this.enable = enable;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

}
