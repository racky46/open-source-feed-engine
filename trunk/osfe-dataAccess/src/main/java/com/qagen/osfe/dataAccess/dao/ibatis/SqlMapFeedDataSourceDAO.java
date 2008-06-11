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

import com.qagen.osfe.dataAccess.dao.FeedDataSourceDAO;
import com.qagen.osfe.dataAccess.param.LimitParam;
import com.qagen.osfe.dataAccess.vo.FeedDataSource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedDataSourceDAO extends SqlMapClientDaoSupport implements FeedDataSourceDAO {

  public void insert(FeedDataSource model) {
    getSqlMapClientTemplate().insert("FeedDataSource.insert", model);
  }

  public void update(FeedDataSource model) {
    getSqlMapClientTemplate().update("FeedDataSource.update", model);
  }

  public void delete(FeedDataSource model) {
    getSqlMapClientTemplate().delete("FeedDataSource.delete", model);
  }

  public FeedDataSource findByPrimaryId(Object primaryId) {
    return (FeedDataSource) getSqlMapClientTemplate().queryForObject("FeedDataSource.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedDataSource> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedDataSource.findAll");
  }

  @SuppressWarnings("unchecked")
  public List<FeedDataSource> findAllWithLimit(LimitParam limits) {
    return getSqlMapClientTemplate().queryForList("FeedDataSource.findAllWithLimit", limits);

  }
}
