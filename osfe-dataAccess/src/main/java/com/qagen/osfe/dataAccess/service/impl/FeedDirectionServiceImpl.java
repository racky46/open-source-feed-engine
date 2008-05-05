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

import com.qagen.osfe.dataAccess.dao.FeedDirectionDAO;
import com.qagen.osfe.dataAccess.service.FeedDirectionService;
import com.qagen.osfe.dataAccess.vo.FeedDirection;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedDirectionServiceImpl implements FeedDirectionService {
  private FeedDirectionDAO feedDirectionDAO;

  public void setFeedDirectionDAO(FeedDirectionDAO feedDirectionDAO) {
    this.feedDirectionDAO = feedDirectionDAO;
  }

  @Transactional
  public void insert(FeedDirection model) {
    feedDirectionDAO.insert(model);
  }

  @Transactional
  public void update(FeedDirection model) {
    feedDirectionDAO.update(model);
  }

  @Transactional
  public void delete(FeedDirection model) {
    feedDirectionDAO.delete(model);
  }

  @Transactional
  public FeedDirection findByPrimaryId(String primaryId) {
    return feedDirectionDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedDirection> findAll() {
    return feedDirectionDAO.findAll();
  }
}
