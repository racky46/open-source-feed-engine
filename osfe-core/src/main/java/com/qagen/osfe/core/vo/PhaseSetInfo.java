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

public class PhaseSetInfo {
  private List<PhaseInfo> preFeedFilePhases;
  private List<PhaseInfo> preEventPhaseList;
  private List<PhaseInfo> batchPhaseList;
  private List<PhaseInfo> postEventPhaseList;

  private Integer batchSize;

  public PhaseSetInfo(Integer batchSize) {
    this.batchSize = batchSize;
  }

  public List<PhaseInfo> getPreFeedFilePhases() {
    return preFeedFilePhases;
  }

  public void setPreFeedFilePhases(List<PhaseInfo> preFeedFilePhases) {
    this.preFeedFilePhases = preFeedFilePhases;
  }

  public List<PhaseInfo> getPreEventPhaseList() {
    return preEventPhaseList;
  }

  public void setPreEventPhaseList(List<PhaseInfo> preEventPhaseList) {
    this.preEventPhaseList = preEventPhaseList;
  }

  public List<PhaseInfo> getBatchPhaseList() {
    return batchPhaseList;
  }

  public void setBatchPhaseList(List<PhaseInfo> batchPhaseList) {
    this.batchPhaseList = batchPhaseList;
  }

  public List<PhaseInfo> getPostEventPhaseList() {
    return postEventPhaseList;
  }

  public void setPostEventPhaseList(List<PhaseInfo> postEventPhaseList) {
    this.postEventPhaseList = postEventPhaseList;
  }

  public Integer getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(Integer batchSize) {
    this.batchSize = batchSize;
  }
}
