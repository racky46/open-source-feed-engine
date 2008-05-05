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

import com.qagen.osfe.dataAccess.dao.FeedTypeDAO;
import com.qagen.osfe.dataAccess.service.FeedTypeService;
import com.qagen.osfe.dataAccess.vo.FeedType;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class FeedTypeServiceImpl implements FeedTypeService {
  private FeedTypeDAO feedTypeDAO;

  public void setFeedTypeDAO(FeedTypeDAO feedTypeDAO) {
    this.feedTypeDAO = feedTypeDAO;
  }

  @Transactional
  public void insert(FeedType model) {
    feedTypeDAO.insert(model);
  }

  @Transactional
  public void update(FeedType model) {
    feedTypeDAO.update(model);
  }

  @Transactional
  public void delete(FeedType model) {
    feedTypeDAO.delete(model);
  }

  @Transactional
  public FeedType findByPrimaryId(Integer primaryId) {
    return feedTypeDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedType> findAll() {
    return feedTypeDAO.findAll();
  }
}
