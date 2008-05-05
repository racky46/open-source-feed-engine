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
package com.qagen.osfe.dataAccess.service.impl;

import com.qagen.osfe.dataAccess.dao.FeedJobDAO;
import com.qagen.osfe.dataAccess.service.FeedJobService;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedJobServiceImpl implements FeedJobService {
  private FeedJobDAO feedJobDAO;

  public void setFeedJobDAO(FeedJobDAO feedJobDAO) {
    this.feedJobDAO = feedJobDAO;
  }

  @Transactional
  public void insert(FeedJob model) {
    feedJobDAO.insert(model);
  }

  @Transactional
  public void update(FeedJob model) {
    feedJobDAO.update(model);
  }

  @Transactional
  public void delete(FeedJob model) {
    feedJobDAO.delete(model);
  }

  @Transactional
  public FeedJob findByPrimaryId(Integer primaryId) {
    return feedJobDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedJob> findByFeedJobStateId(String stateId) {
    return feedJobDAO.findByFeedJobStateId(stateId);
  }

  @Transactional
  public List<FeedJob> findByFeedFileId(Integer feedFileId) {
    return feedJobDAO.findByFeedFileId(feedFileId);
  }

}
