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

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This is the value object class for table t_feed_phase_stats
 *
 * @table t_feed_phase_stats
 */
public class FeedPhaseStats extends VO {
  private Integer feedPhaseStatId;
  private String phaseId;
  private Double avgProcessingTime = 0D;
  private Integer iterationCount = 0;
  private Integer feedFileId;
  private Long totalTimeInMs = 0L;
  
  // Use to temporarily store time stamp when track phase stats.
  private Long startTime = 0L;

  public FeedPhaseStats() {
  }

  public FeedPhaseStats(String phaseId, Integer feedFileId) {
    this.phaseId = phaseId;
    this.feedFileId = feedFileId;
  }

  public Integer getFeedPhaseStatId() {
    return feedPhaseStatId;
  }

  public void setFeedPhaseStatId(Integer feedPhaseStatId) {
    this.feedPhaseStatId = feedPhaseStatId;
  }

  public String getPhaseId() {
    return phaseId;
  }

  public void setPhaseId(String phaseId) {
    this.phaseId = phaseId;
  }

  public Double getAvgProcessingTime() {
    return avgProcessingTime;
  }

  public void setAvgProcessingTime(Double avgProcessingTime) {
    this.avgProcessingTime = avgProcessingTime;
  }

  public Integer getIterationCount() {
    return iterationCount;
  }

  public void setIterationCount(Integer iterationCount) {
    this.iterationCount = iterationCount;
  }

  public Integer getFeedFileId() {
    return feedFileId;
  }

  public void setFeedFileId(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  public Long getStartTime() {
    return startTime;
  }

  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  public Long getTotalTimeInMs() {
    return totalTimeInMs;
  }

  public void setTotalTimeInMs(Long totalTimeInMs) {
    this.totalTimeInMs = totalTimeInMs;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof FeedPhaseStats)) {
      return false;
    }

    final FeedPhaseStats model = (FeedPhaseStats) object;
    if (feedPhaseStatId.equals(model.feedPhaseStatId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedPhaseStatId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedPhaseStatId.toString());

    return toString(list);
  }

}