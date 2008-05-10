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

import com.qagen.osfe.dataAccess.dao.FeedPhaseStatsDAO;
import com.qagen.osfe.dataAccess.service.FeedPhaseStatsService;
import com.qagen.osfe.dataAccess.vo.FeedPhaseStats;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedPhaseStatsServiceImpl implements FeedPhaseStatsService {
  private FeedPhaseStatsDAO feedPhaseStatsDAO;

  public void setFeedPhaseStatsDAO(FeedPhaseStatsDAO feedPhaseStatsDAO) {
    this.feedPhaseStatsDAO = feedPhaseStatsDAO;
  }

  @Transactional
  public void insert(FeedPhaseStats model) {
    feedPhaseStatsDAO.insert(model);
  }

  @Transactional
  public void update(FeedPhaseStats model) {
    feedPhaseStatsDAO.update(model);
  }

  @Transactional
  public void delete(FeedPhaseStats model) {
    feedPhaseStatsDAO.delete(model);
  }

  @Transactional
  public void insert(List<FeedPhaseStats> list) {
    for (FeedPhaseStats feedPhaseStats : list) {
      this.feedPhaseStatsDAO.insert(feedPhaseStats);
    }
  }

  @Transactional
  public void update(List<FeedPhaseStats> list) {
    for (FeedPhaseStats feedPhaseStats : list) {
      this.feedPhaseStatsDAO.update(feedPhaseStats);
    }
  }

  @Transactional
  public FeedPhaseStats findByPrimaryId(Integer primaryId) {
    return feedPhaseStatsDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedPhaseStats> findByFeedFileId(Integer feedFileId) {
    return feedPhaseStatsDAO.findByFeedFileId(feedFileId);
  }
}