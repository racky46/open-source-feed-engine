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

import com.qagen.osfe.dataAccess.dao.CheckPointDAO;
import com.qagen.osfe.dataAccess.service.CheckpointService;
import com.qagen.osfe.dataAccess.vo.Checkpoint;
import org.springframework.transaction.annotation.Transactional;

public class CheckpointServiceImpl implements CheckpointService {
  private CheckPointDAO checkPointDAO;

  public void setCheckPointDAO(CheckPointDAO feedTypeDAO) {
    this.checkPointDAO = feedTypeDAO;
  }

  @Transactional
  public void insert(Checkpoint model) {
    checkPointDAO.insert(model);
  }

  @Transactional
  public void update(Checkpoint model) {
    checkPointDAO.update(model);
  }

  @Transactional
  public void delete(Checkpoint model) {
    checkPointDAO.delete(model);
  }

  @Transactional
  public Checkpoint findByPrimaryId(Integer primaryId) {
    return checkPointDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public Checkpoint findByFeedFileId(Integer feedFileId) {
    return checkPointDAO.findByFeedFileId(feedFileId);
  }
}