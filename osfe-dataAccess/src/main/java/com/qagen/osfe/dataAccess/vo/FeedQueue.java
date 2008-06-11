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
import java.util.List;
import java.util.ArrayList;

/**
 * Author: Hycel Taylor
 * <p/>
 * This is the value object class for table t_feed_mapped_queue
 *
 * @table t_feed_mapped_queue
 */
public class FeedQueue extends VO {
  private Integer feedQueueId;
  private Timestamp entryTime;
  private String feedFileName;
  private Feed feed;
  private FeedQueueType feedQueueType;
  private FeedMonitor feedMonitor;
  private FeedQueueState feedQueueState;

  public FeedQueue() {
  }

  public FeedQueue(FeedQueueType feedQueueType, FeedMonitor feedMonitor, FeedQueueState feedQueueState, Feed feed, String feedFileName) {
    this.feedQueueType = feedQueueType;
    this.feedMonitor = feedMonitor;
    this.feedQueueState = feedQueueState;
    this.feed = feed;
    this.entryTime = new Timestamp(System.currentTimeMillis());
    this.feedFileName = feedFileName;
  }

  public Integer getFeedQueueId() {
    return feedQueueId;
  }

  public void setFeedQueueId(Integer feedQueueId) {
    this.feedQueueId = feedQueueId;
  }

  public FeedMonitor getFeedMonitor() {
    return feedMonitor;
  }

  public void setFeedMonitor(FeedMonitor feedMonitor) {
    this.feedMonitor = feedMonitor;
  }

  public FeedQueueState getFeedQueueState() {
    return feedQueueState;
  }

  public void setFeedQueueState(FeedQueueState feedQueueState) {
    this.feedQueueState = feedQueueState;
  }

  public FeedQueueType getFeedQueueType() {
    return feedQueueType;
  }

  public void setFeedQueueType(FeedQueueType feedQueueType) {
    this.feedQueueType = feedQueueType;
  }

  public Timestamp getEntryTime() {
    return entryTime;
  }

  public void setEntryTime(Timestamp entryTime) {
    this.entryTime = entryTime;
  }

  public String getFeedFileName() {
    return feedFileName;
  }

  public void setFeedFileName(String feedFileName) {
    this.feedFileName = feedFileName;
  }

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof FeedQueue)) {
      return false;
    }

    final FeedQueue model = (FeedQueue) object;
    if (feedQueueId.equals(model.feedQueueId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedQueueId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedQueueId.toString());

    return toString(list);
  }
}
