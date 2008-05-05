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

import com.qagen.osfe.dataAccess.dao.FeedJobStateDAO;
import com.qagen.osfe.dataAccess.vo.FeedJobState;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedJobStateDAO extends SqlMapClientDaoSupport implements FeedJobStateDAO {

  public void insert(FeedJobState model) {
    getSqlMapClientTemplate().insert("FeedJobState.insert", model);
  }

  public void update(FeedJobState model) {
    getSqlMapClientTemplate().update("FeedJobState.update", model);
  }

  public void delete(FeedJobState model) {
    getSqlMapClientTemplate().delete("FeedJobState.delete", model);
  }

  public FeedJobState findByPrimaryId(Object primaryId) {
    return (FeedJobState) getSqlMapClientTemplate().queryForObject("FeedJobState.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedJobState> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedJobState.findAll");
  }
}
