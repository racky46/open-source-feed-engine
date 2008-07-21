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

import com.qagen.osfe.dataAccess.dao.FeedJobDAO;
import com.qagen.osfe.dataAccess.param.LimitParam;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedJobDAO extends SqlMapClientDaoSupport implements FeedJobDAO {

  public void insert(FeedJob model) {
    getSqlMapClientTemplate().insert("FeedJob.insert", model);
  }

  public void update(FeedJob model) {
    getSqlMapClientTemplate().update("FeedJob.update", model);
  }

  public void delete(FeedJob model) {
    getSqlMapClientTemplate().delete("FeedJob.delete", model);
  }

  public FeedJob findByPrimaryId(Object primaryId) {
    return (FeedJob) getSqlMapClientTemplate().queryForObject("FeedJob.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedJob> findByFeedJobStateId(String stateId) {
    return getSqlMapClientTemplate().queryForList("FeedJob.findByFeedJobStateId", stateId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedJob> findByFeedFileId(Integer feedFileId) {
    return getSqlMapClientTemplate().queryForList("FeedJob.findByFeedFileId", feedFileId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedJob> findByActiveFailedJobStateId() {
    return getSqlMapClientTemplate().queryForList("FeedJob.findByActiveFailedJobStateId");
  }
  
  @SuppressWarnings("unchecked")
  public List<FeedJob> findByActiveFailedJobStateIdWithLimit(LimitParam limitParam) {
    return getSqlMapClientTemplate().queryForList("FeedJob.findByActiveFailedJobStateIdWithLimit", limitParam);
  }
}
