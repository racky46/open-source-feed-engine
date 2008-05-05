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

import com.qagen.osfe.dataAccess.dao.FeedGroupDAO;
import com.qagen.osfe.dataAccess.service.FeedGroupService;
import com.qagen.osfe.dataAccess.vo.FeedGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedGroupServiceImpl implements FeedGroupService {
  private FeedGroupDAO feedGroupDAO;

  public void setFeedGroupDAO(FeedGroupDAO feedGroupDAO) {
    this.feedGroupDAO = feedGroupDAO;
  }

  @Transactional
  public void insert(FeedGroup model) {
    feedGroupDAO.insert(model);
  }

  @Transactional
  public void update(FeedGroup model) {
    feedGroupDAO.update(model);
  }

  @Transactional
  public void delete(FeedGroup model) {
    feedGroupDAO.delete(model);
  }

  @Transactional
  public FeedGroup findByPrimaryId(Integer primaryId) {
    return feedGroupDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedGroup> findAll() {
    return feedGroupDAO.findAll();
  }
}
