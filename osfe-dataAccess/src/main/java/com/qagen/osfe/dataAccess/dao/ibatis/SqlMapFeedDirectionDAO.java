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

import com.qagen.osfe.dataAccess.dao.FeedDirectionDAO;
import com.qagen.osfe.dataAccess.vo.FeedDirection;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedDirectionDAO extends SqlMapClientDaoSupport implements FeedDirectionDAO {

  public void insert(FeedDirection model) {
    getSqlMapClientTemplate().insert("FeedDirection.insert", model);
  }

  public void update(FeedDirection model) {
    getSqlMapClientTemplate().update("FeedDirection.update", model);
  }

  public void delete(FeedDirection model) {
    getSqlMapClientTemplate().delete("FeedDirection.delete", model);
  }

  public FeedDirection findByPrimaryId(Object primaryId) {
    return (FeedDirection) getSqlMapClientTemplate().queryForObject("FeedDirection.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedDirection> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedDirection.findAll");
  }
}
