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

import com.qagen.osfe.dataAccess.dao.FeedFileStateDAO;
import com.qagen.osfe.dataAccess.service.FeedFileStateService;
import com.qagen.osfe.dataAccess.vo.FeedFileState;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedFileStateServiceImpl implements FeedFileStateService {
  private FeedFileStateDAO dao;

  public void setDao(FeedFileStateDAO dao) {
    this.dao = dao;
  }

  @Transactional
  public void insert(FeedFileState model) {
    dao.insert(model);
  }

  @Transactional
  public void update(FeedFileState model) {
    dao.update(model);
  }

  @Transactional
  public void delete(FeedFileState model) {
    dao.delete(model);
  }

  @Transactional
  public FeedFileState findByPrimaryId(Integer primaryId) {
    return dao.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<FeedFileState> findAll() {
    return dao.findAll();
  }
}
