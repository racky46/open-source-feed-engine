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

import com.qagen.osfe.dataAccess.dao.FeedCheckpointDAO;
import com.qagen.osfe.dataAccess.service.FeedCheckpointService;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;
import org.springframework.transaction.annotation.Transactional;

public class FeedCheckpointServiceImpl implements FeedCheckpointService {
  private FeedCheckpointDAO checkpointDAO;

  public void setCheckpointDAO(FeedCheckpointDAO checkpointDAO) {
    this.checkpointDAO = checkpointDAO;
  }

  @Transactional
  public void insert(FeedCheckpoint model) {
    checkpointDAO.insert(model);
  }

  @Transactional
  public void update(FeedCheckpoint model) {
    checkpointDAO.update(model);
  }

  @Transactional
  public void delete(FeedCheckpoint model) {
    checkpointDAO.delete(model);
  }

  @Transactional
  public FeedCheckpoint findByPrimaryId(Integer primaryId) {
    return checkpointDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public FeedCheckpoint findByFeedFileId(Integer feedFileId) {
    return checkpointDAO.findByFeedFileId(feedFileId);
  }
}