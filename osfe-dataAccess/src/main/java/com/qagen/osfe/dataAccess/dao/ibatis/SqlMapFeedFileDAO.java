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
package com.qagen.osfe.dataAccess.dao.ibatis;

import com.qagen.osfe.dataAccess.dao.FeedFileDAO;
import com.qagen.osfe.dataAccess.param.FeedFileFilterParam;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedFileDAO extends SqlMapClientDaoSupport implements FeedFileDAO {

  public void insert(FeedFile model) {
    getSqlMapClientTemplate().insert("FeedFile.insert", model);
  }

  public void update(FeedFile model) {
    getSqlMapClientTemplate().update("FeedFile.update", model);
  }

  public void delete(FeedFile model) {
    getSqlMapClientTemplate().delete("FeedFile.delete", model);
  }

  public FeedFile findByPrimaryId(Object primaryId) {
    return (FeedFile) getSqlMapClientTemplate().queryForObject("FeedFile.findByPrimaryId", primaryId);
  }

  public FeedFile findByFeedFileName(String feedFileName) {
    return (FeedFile) getSqlMapClientTemplate().queryForObject("FeedFile.findByFeedFileName", feedFileName);
  }

  @SuppressWarnings("unchecked")
  public List<FeedFile> findAllProcessingFeeds() {
    return getSqlMapClientTemplate().queryForList("FeedFile.findAllProcessingFeeds");
  }

  @SuppressWarnings("unchecked")
  public List<FeedFile> findAllProcessingFeedsForFeedId(String feedId) {
    return getSqlMapClientTemplate().queryForList("FeedFile.findAllProcessingFeedsForFeedId", feedId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedFile> findAllFailedFeedsForFeedId(String feedId) {
    return getSqlMapClientTemplate().queryForList("FeedFile.findAllFailedFeedsForFeedId", feedId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedFile> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedFile.findAll");    
  }

  @SuppressWarnings("unchecked")
  public List<FeedFile> findByFeedFileState(String feedFileStateId) {
    return getSqlMapClientTemplate().queryForList("FeedFile.findByFeedFileState", feedFileStateId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedFile> findByFeedFileStateAndDate(FeedFileFilterParam param) {
    return getSqlMapClientTemplate().queryForList("FeedFile.findByFeedFileStateAndDate", param); 
  }
}
