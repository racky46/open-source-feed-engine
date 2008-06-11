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

import com.qagen.osfe.dataAccess.dao.FeedQueueTypeDAO;
import com.qagen.osfe.dataAccess.service.FeedQueueTypeService;
import com.qagen.osfe.dataAccess.vo.FeedQueueType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedQueueTypeServiceImpl implements FeedQueueTypeService {
  private FeedQueueTypeDAO feedQueueTypeDAO;

  public void setFeedQueueTypeDAO(FeedQueueTypeDAO feedQueueTypeDAO) {
    this.feedQueueTypeDAO = feedQueueTypeDAO;
  }

  @Transactional
  public void insert(FeedQueueType model) {
    feedQueueTypeDAO.insert(model);
  }

  @Transactional
  public void update(FeedQueueType model) {
    feedQueueTypeDAO.update(model);
  }

  @Transactional
  public void delete(FeedQueueType model) {
    feedQueueTypeDAO.delete(model);
  }

  @Transactional
  public FeedQueueType findByPrimaryId(String primaryId) {
    return feedQueueTypeDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedQueueType> findAll() {
    return feedQueueTypeDAO.findAll();
  }
}