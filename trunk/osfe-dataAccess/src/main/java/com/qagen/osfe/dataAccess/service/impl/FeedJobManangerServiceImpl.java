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
import com.qagen.osfe.dataAccess.dao.FeedJobDAO;
import com.qagen.osfe.dataAccess.service.FeedJobManagerService;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.springframework.transaction.annotation.Transactional;

public class FeedJobManangerServiceImpl implements FeedJobManagerService {
  private FeedJobDAO feedJobDAO;
  private FeedFileDAO feedFileDAO;

  public void setFeedJobDAO(FeedJobDAO feedJobDAO) {
    this.feedJobDAO = feedJobDAO;
  }

  public void setFeedFileDAO(FeedFileDAO feedFileDAO) {
    this.feedFileDAO = feedFileDAO;
  }

  @Transactional
  public void updateFeedJobAndFeedFile(FeedJob feedJob, FeedFile feedFile) {
    feedJobDAO.update(feedJob);
    feedFileDAO.update(feedFile);
  }

  @Transactional
  public void createFeedJob(FeedFile feedFile, FeedJob feedJob) {
    feedJobDAO.insert(feedJob);
    feedFileDAO.update(feedFile);
  }

  @Transactional
  public void createFeedFileAndFeedJob(FeedFile feedFile, FeedJob feedJob) {
    feedFileDAO.insert(feedFile);
    feedJobDAO.insert(feedJob);
  }

}
