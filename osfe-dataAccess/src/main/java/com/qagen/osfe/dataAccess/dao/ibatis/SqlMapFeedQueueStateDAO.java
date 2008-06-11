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

import com.qagen.osfe.dataAccess.dao.FeedQueueStateDAO;
import com.qagen.osfe.dataAccess.vo.FeedQueueState;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedQueueStateDAO extends SqlMapClientDaoSupport implements FeedQueueStateDAO {

  public void insert(FeedQueueState model) {
    getSqlMapClientTemplate().insert("FeedQueueState.insert", model);
  }

  public void update(FeedQueueState model) {
    getSqlMapClientTemplate().update("FeedQueueState.update", model);
  }

  public void delete(FeedQueueState model) {
    getSqlMapClientTemplate().delete("FeedQueueState.delete", model);
  }

  public FeedQueueState findByPrimaryId(Object primaryId) {
    return (FeedQueueState) getSqlMapClientTemplate().queryForObject("FeedQueueState.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedQueueState> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedQueueState.findAll");
  }
}