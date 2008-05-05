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

/**
 * model class generate from table t_feed_direction
 *
 * @table t_feed_direction
 */
public class FeedDirection extends VO {
  private String feedDirectionId;
  private String description;

  public FeedDirection() {
  }

  public String getFeedDirectionId() {
    return feedDirectionId;
  }

  public void setFeedDirectionId(String feedDirectionId) {
    this.feedDirectionId = feedDirectionId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
