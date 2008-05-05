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

import com.qagen.osfe.dataAccess.dao.FeedFileStateDAO;
import com.qagen.osfe.dataAccess.vo.FeedFileState;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedFileStateDAO extends SqlMapClientDaoSupport implements FeedFileStateDAO {

  public void insert(FeedFileState model) {
    getSqlMapClientTemplate().insert("FeedFileState.insert", model);
  }

  public void update(FeedFileState model) {
    getSqlMapClientTemplate().update("FeedFileState.update", model);
  }

  public void delete(FeedFileState model) {
    getSqlMapClientTemplate().delete("FeedFileState.delete", model);
  }

  public FeedFileState findByPrimaryId(Object primaryId) {
    return (FeedFileState) getSqlMapClientTemplate().queryForObject("FeedFileState.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedFileState> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedFileState.findAll");
  }
}
