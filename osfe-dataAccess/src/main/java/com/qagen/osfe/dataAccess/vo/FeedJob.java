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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * model class generate from table t_feed_job
 *
 * @table t_feed_job
 */
public class FeedJob extends VO {
  private Integer feedJobId;
  private Timestamp processingStart;
  private Timestamp processingEnd;
  private Integer failedRowNumber;
  private String failureCode;
  private String failureMessage;
  private FeedJobState feedJobState;
  private FeedFile feedFile;

  public FeedJob() {
  }

  public Integer getFeedJobId() {
    return feedJobId;
  }

  public void setFeedJobId(Integer feedJobId) {
    this.feedJobId = feedJobId;
  }

  public Timestamp getProcessingStart() {
    return processingStart;
  }

  public void setProcessingStart(Timestamp processingStart) {
    this.processingStart = processingStart;
  }

  public Timestamp getProcessingEnd() {
    return processingEnd;
  }

  public void setProcessingEnd(Timestamp processingEnd) {
    this.processingEnd = processingEnd;
  }

  public Integer getFailedRowNumber() {
    return failedRowNumber;
  }

  public void setFailedRowNumber(Integer failedRowNumber) {
    this.failedRowNumber = failedRowNumber;
  }

  public String getFailureCode() {
    return failureCode;
  }

  public void setFailureCode(String failureCode) {
    this.failureCode = failureCode;
  }

  public String getFailureMessage() {
    return failureMessage;
  }

  public void setFailureMessage(String failureMessage) {
    this.failureMessage = failureMessage;
  }

  public FeedJobState getFeedJobState() {
    return feedJobState;
  }

  public void setFeedJobState(FeedJobState feedJobState) {
    this.feedJobState = feedJobState;
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

    if (!(object instanceof FeedJob)) {
      return false;
    }

    final FeedJob model = (FeedJob) object;
    if (feedJobId.equals(model.feedJobId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedJobId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedJobId.toString());

    return toString(list);
  }
}
