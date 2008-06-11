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

import com.qagen.osfe.dataAccess.dao.FeedQueueTypeDAO;
import com.qagen.osfe.dataAccess.vo.FeedQueueType;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedQueueTypeDAO extends SqlMapClientDaoSupport implements FeedQueueTypeDAO {

  public void insert(FeedQueueType model) {
    getSqlMapClientTemplate().insert("FeedQueueType.insert", model);
  }

  public void update(FeedQueueType model) {
    getSqlMapClientTemplate().update("FeedQueueType.update", model);
  }

  public void delete(FeedQueueType model) {
    getSqlMapClientTemplate().delete("FeedQueueType.delete", model);
  }

  public FeedQueueType findByPrimaryId(Object primaryId) {
    return (FeedQueueType) getSqlMapClientTemplate().queryForObject("FeedQueueType.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedQueueType> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedQueueType.findAll");
  }
}