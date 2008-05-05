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

import com.qagen.osfe.dataAccess.dao.FeedQueueDAO;
import com.qagen.osfe.dataAccess.param.MonitorQueueParam;
import com.qagen.osfe.dataAccess.service.FeedQueueService;
import com.qagen.osfe.dataAccess.vo.FeedQueue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedQueueServiceImpl implements FeedQueueService {
  private FeedQueueDAO feedQueueDAO;

  public void setFeedMappedQueueDAO(FeedQueueDAO feedDAO) {
    this.feedQueueDAO = feedDAO;
  }

  @Transactional
  public void insert(FeedQueue model) {
    feedQueueDAO.insert(model);
  }

  @Transactional
  public void update(FeedQueue model) {
    feedQueueDAO.update(model);
  }

  @Transactional
  public void delete(FeedQueue model) {
    feedQueueDAO.delete(model);
  }

  @Transactional
  public FeedQueue findByPrimaryId(Integer primaryId) {
    return feedQueueDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedQueue> findAll(Integer monitorId) {
    return feedQueueDAO.findAll(monitorId);
  }

  @Transactional
  public void deleteAll(Integer monitorId) {
    feedQueueDAO.deleteAll(monitorId);
  }

  public void deleteByMonitorIdQueueId(MonitorQueueParam param) {
    feedQueueDAO.deleteByMonitorIdQueueId(param);
  }

  public List<FeedQueue> findByMonitorIdQueueId(MonitorQueueParam param) {
    return feedQueueDAO.findByMonitorIdQueueId(param);
  }
}
