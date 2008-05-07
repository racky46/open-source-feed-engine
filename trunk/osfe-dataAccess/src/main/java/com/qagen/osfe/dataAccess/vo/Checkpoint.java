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
package com.qagen.osfe.dataAccess.vo;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;
import java.util.ArrayList;

/**
 * model class generate from table t_check_point
 *
 * @table t_check_point
 */
public class Checkpoint extends VO {
  private Integer checkpointId;
  private String phaseId;
  private Integer currentFileIndex;
  private FeedFile feedFile;

  public Checkpoint() {
  }

  public Checkpoint(String phaseId, Integer currentFileIndex, FeedFile feedFile) {
    this.phaseId = phaseId;
    this.currentFileIndex = currentFileIndex;
    this.feedFile = feedFile;
  }

  public Integer getCheckpointId() {
    return checkpointId;
  }

  public void setCheckpointId(Integer checkpointId) {
    this.checkpointId = checkpointId;
  }

  public String getPhaseId() {
    return phaseId;
  }

  public void setPhaseId(String phaseId) {
    this.phaseId = phaseId;
  }

  public Integer getCurrentFileIndex() {
    return currentFileIndex;
  }

  public void setCurrentFileIndex(Integer currentFileIndex) {
    this.currentFileIndex = currentFileIndex;
  }

  public FeedFile getFeedFile() {
    return feedFile;
  }

  public void setFeedFile(FeedFile feedFile) {
    this.feedFile = feedFile;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof Checkpoint)) {
      return false;
    }

    final Checkpoint model = (Checkpoint) object;
    if (checkpointId.equals(model.checkpointId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(checkpointId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(checkpointId.toString());

    return toString(list);
  }
}