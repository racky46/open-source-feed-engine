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

import com.qagen.osfe.dataAccess.dao.FeedMonitorDAO;
import com.qagen.osfe.dataAccess.service.FeedMonitorService;
import com.qagen.osfe.dataAccess.vo.FeedMonitor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedMonitorServiceImpl implements FeedMonitorService {
  private FeedMonitorDAO feedMonitorDAO;

  public void setFeedMonitorDAO(FeedMonitorDAO feedMonitorDAO) {
    this.feedMonitorDAO = feedMonitorDAO;
  }

  @Transactional
  public void insert(FeedMonitor model) {
    feedMonitorDAO.insert(model);
  }

  @Transactional
  public void update(FeedMonitor model) {
    feedMonitorDAO.update(model);
  }

  @Transactional
  public void delete(FeedMonitor model) {
    feedMonitorDAO.delete(model);
  }

  @Transactional
  public FeedMonitor findByPrimaryId(String primaryId) {
    return feedMonitorDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedMonitor> findAll() {
    return feedMonitorDAO.findAll();
  }
}