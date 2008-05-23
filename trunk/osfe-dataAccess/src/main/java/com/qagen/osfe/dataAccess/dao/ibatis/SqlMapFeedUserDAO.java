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

import com.qagen.osfe.dataAccess.dao.FeedUserDAO;
import com.qagen.osfe.dataAccess.vo.FeedUser;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedUserDAO extends SqlMapClientDaoSupport implements FeedUserDAO {

  public void insert(FeedUser model) {
    getSqlMapClientTemplate().insert("FeedUser.insert", model);
  }

  public void update(FeedUser model) {
    getSqlMapClientTemplate().update("FeedUser.update", model);
  }

  public void delete(FeedUser model) {
    getSqlMapClientTemplate().delete("FeedUser.delete", model);
  }

  public FeedUser findByPrimaryId(Object primaryId) {
    return (FeedUser) getSqlMapClientTemplate().queryForObject("FeedUser.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedUser> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedUser.findAll");
  }
}