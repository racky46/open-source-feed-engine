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

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class SaxEventPhaseInfo {
  private String nameSpace;
  private EventTypeEnum event;
  private List<PhaseInfo> phaseInfoList;

  public SaxEventPhaseInfo(String nameSpace, EventTypeEnum event, List<PhaseInfo> phaseInfoList) {
    this.nameSpace = nameSpace;
    this.event = event;
    this.phaseInfoList = phaseInfoList;
  }

  public String getNameSpace() {
    return nameSpace;
  }

  public void setNameSpace(String nameSpace) {
    this.nameSpace = nameSpace;
  }

  public EventTypeEnum getEvent() {
    return event;
  }

  public void setEvent(EventTypeEnum event) {
    this.event = event;
  }

  public List<PhaseInfo> getPhaseInfoList() {
    return phaseInfoList;
  }

  public void setPhaseInfoList(List<PhaseInfo> phaseInfoList) {
    this.phaseInfoList = phaseInfoList;
  }
}
