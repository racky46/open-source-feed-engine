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

import java.sql.Timestamp;

/**
 * model class generate from table t_feed_mapped_queue
 *
 * @table t_feed_mapped_queue
 */
public class FeedQueue extends VO {
  private Integer feedQueueId;
  private String queueId;
  private Integer monitorId;
  private Timestamp entryTime;
  private String feedFileName;
  private Feed feed;

  public FeedQueue() {
  }

  public FeedQueue(String queueId, Feed feed, String feedFileName) {
    this.queueId = queueId;
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

  public String getQueueId() {
    return queueId;
  }

  public void setQueueId(String queueId) {
    this.queueId = queueId;
  }

  public Integer getMonitorId() {
    return monitorId;
  }

  public void setMonitorId(Integer monitorId) {
    this.monitorId = monitorId;
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
}