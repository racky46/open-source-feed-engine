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

import com.qagen.osfe.dataAccess.dao.FeedFileDAO;
import com.qagen.osfe.dataAccess.service.FeedFileService;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FeedFileServiceImpl implements FeedFileService {
  private FeedFileDAO feedFileDAO;

  public void setFeedFileDAO(FeedFileDAO feedFileDAO) {
    this.feedFileDAO = feedFileDAO;
  }

  @Transactional
  public void insert(FeedFile model) {
    feedFileDAO.insert(model);
  }

  @Transactional
  public void update(FeedFile model) {
    feedFileDAO.update(model);
  }

  @Transactional
  public void delete(FeedFile model) {
    feedFileDAO.delete(model);
  }

  @Transactional
  public FeedFile findByPrimaryId(Integer primaryId) {
    return feedFileDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public FeedFile findByFeedFileName(String feedFileName) {
    return feedFileDAO.findByFeedFileName(feedFileName);
  }

  @Transactional
  public List<FeedFile> findAllProcessingFeeds() {
    return feedFileDAO.findAllProcessingFeeds();
  }

  @Transactional
  public List<FeedFile> findAllProcessingFeedsForFeedId(String feedId) {
    return feedFileDAO.findAllProcessingFeedsForFeedId(feedId);
  }

  @Transactional
  public List<FeedFile> findAllFailedFeedsForFeedId(String feedId) {
    return feedFileDAO.findAllFailedFeedsForFeedId(feedId);
  }

  @Transactional
  public List<FeedFile> findAll() {
    return feedFileDAO.findAll();
  }

  @Transactional
  public List<FeedFile> findByFeedFileState(String feedFileStateId) {
    return feedFileDAO.findByFeedFileState(feedFileStateId);
  }
}
