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
 * This is the value object class for table t_feed_queue_state
 *
 * @table t_feed_queue_state
 */
public class FeedQueueState extends VO {
  private String feedQueueStateId;
  private String description;

  public FeedQueueState() {
  }

  public FeedQueueState(String feedQueueStateId) {
    this.feedQueueStateId = feedQueueStateId;
  }

  public String getFeedQueueStateId() {
    return feedQueueStateId;
  }

  public void setFeedQueueStateId(String feedQueueStateId) {
    this.feedQueueStateId = feedQueueStateId;
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

    if (!(object instanceof FeedQueueState)) {
      return false;
    }

    final FeedQueueState model = (FeedQueueState) object;
    if (feedQueueStateId.equals(model.feedQueueStateId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedQueueStateId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedQueueStateId.toString());

    return toString(list);
  }
}