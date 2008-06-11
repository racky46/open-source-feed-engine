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
 * This is the value object class for table t_feed_monitor
 *
 * @table t_feed_monitor
 */
public class FeedMonitor extends VO {
  private String feedMonitorId;
  private String description;

  public FeedMonitor() {
  }

  public String getFeedMonitorId() {
    return feedMonitorId;
  }

  public void setFeedMonitorId(String feedMonitorId) {
    this.feedMonitorId = feedMonitorId;
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

    if (!(object instanceof FeedMonitor)) {
      return false;
    }

    final FeedMonitor model = (FeedMonitor) object;
    if (feedMonitorId.equals(model.feedMonitorId)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder(17, 37);
    builder.append(feedMonitorId);
    return builder.toHashCode();
  }

  @Override
  public String toString() {
    final List<String> list = new ArrayList<String>();

    list.add(feedMonitorId);

    return toString(list);
  }
}