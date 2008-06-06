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

import com.qagen.osfe.dataAccess.dao.FeedDAO;
import com.qagen.osfe.dataAccess.service.FeedService;
import com.qagen.osfe.dataAccess.vo.Feed;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedServiceImpl implements FeedService {
  private FeedDAO feedDAO;

  public void setFeedDAO(FeedDAO feedDAO) {
    this.feedDAO = feedDAO;
  }

  @Transactional
  public void insert(Feed model) {
    feedDAO.insert(model);
  }

  @Transactional
  public void update(Feed model) {
    feedDAO.update(model);
  }

  @Transactional
  public void delete(Feed model) {
    feedDAO.delete(model);
  }

  @Transactional
  public Feed findByPrimaryId(String primaryId) {
    return feedDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<Feed> findAll() {
    return feedDAO.findAll();
  }

}
