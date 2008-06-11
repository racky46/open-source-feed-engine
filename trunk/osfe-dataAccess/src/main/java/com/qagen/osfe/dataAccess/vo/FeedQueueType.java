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
 * Author: Hycel Taylor
 * <p/>
 * This is the value object class for table t_feed_queue_type
 *
 * @table t_feed_queue_type
 */
public class FeedQueueType extends VO {
  private String feedQueueTypeId;
  private Integer maxConcurrentRun;
  private String description;

  public FeedQueueType() {
  }

  public String getFeedQueueTypeId() {
    return feedQueueTypeId;
  }

  public void setFeedQueueTypeId(String feedQueueTypeId) {
    this.feedQueueTypeId = feedQueueTypeId;
  }

  public Integer getMaxConcurrentRun() {
    return maxConcurrentRun;
  }

  public void setMaxConcurrentRun(Integer maxConcurrentRun) {
    this.maxConcurrentRun = maxConcurrentRun;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof FeedQueueType)) {
      return false;
    }

    final FeedQueueType model = (FeedQueueType) object;
    if (feedQueueTypeId.equals(model.feedQueueTypeId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedQueueTypeId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedQueueTypeId);

    return toString(list);
  }
}