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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This is the value object class for table t_feed_file
 *
 * @table t_feed_file
 */
public class FeedFile extends VO {
  private Integer feedFileId;
  private Date feedFileDate;
  private Time feedFileTime;
  private Integer recordsProcessed;
  private Integer recordsRejected;
  private String feedFileName;
  private String feedDocument;
  private Feed feed;
  private FeedFileState FeedFileState;
  private List<FeedJob> feedJobs;

  public FeedFile() {
  }

  public Integer getFeedFileId() {
    return feedFileId;
  }

  public void setFeedFileId(Integer feedFileId) {
    this.feedFileId = feedFileId;
  }

  public Date getFeedFileDate() {
    return feedFileDate;
  }

  public void setFeedFileDate(Date feedFileDate) {
    this.feedFileDate = feedFileDate;
  }

  public Time getFeedFileTime() {
    return feedFileTime;
  }

  public void setFeedFileTime(Time feedFileTime) {
    this.feedFileTime = feedFileTime;
  }

  public Integer getRecordsProcessed() {
    return recordsProcessed;
  }

  public void setRecordsProcessed(Integer recordsProcessed) {
    this.recordsProcessed = recordsProcessed;
  }

  public Integer getRecordsRejected() {
    return recordsRejected;
  }

  public void setRecordsRejected(Integer recordsRejected) {
    this.recordsRejected = recordsRejected;
  }

  public String getFeedFileName() {
    return feedFileName;
  }

  public void setFeedFileName(String feedFileName) {
    this.feedFileName = feedFileName;
  }

  public String getFeedDocument() {
    return feedDocument;
  }

  public void setFeedDocument(String feedDocument) {
    this.feedDocument = feedDocument;
  }

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  public FeedFileState getFeedFileState() {
    return FeedFileState;
  }

  public void setFeedFileState(FeedFileState feedFileState) {
    FeedFileState = feedFileState;
  }

  public List<FeedJob> getFeedJobs() {
    return feedJobs;
  }

  public void setFeedJobs(List<FeedJob> feedJobs) {
    this.feedJobs = feedJobs;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof FeedFile)) {
      return false;
    }

    final FeedFile model = (FeedFile) object;
    if (feedFileId.equals(model.feedFileId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedFileId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedFileId.toString());

    return toString(list);
  }
}
