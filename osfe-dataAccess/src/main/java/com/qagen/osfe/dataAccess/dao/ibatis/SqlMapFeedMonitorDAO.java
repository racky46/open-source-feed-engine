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

import com.qagen.osfe.dataAccess.dao.FeedMonitorDAO;
import com.qagen.osfe.dataAccess.vo.FeedMonitor;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedMonitorDAO extends SqlMapClientDaoSupport implements FeedMonitorDAO {

  public void insert(FeedMonitor model) {
    getSqlMapClientTemplate().insert("FeedMonitor.insert", model);
  }

  public void update(FeedMonitor model) {
    getSqlMapClientTemplate().update("FeedMonitor.update", model);
  }

  public void delete(FeedMonitor model) {
    getSqlMapClientTemplate().delete("FeedMonitor.delete", model);
  }

  public FeedMonitor findByPrimaryId(Object primaryId) {
    return (FeedMonitor) getSqlMapClientTemplate().queryForObject("FeedMonitor.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedMonitor> findAll() {
    return getSqlMapClientTemplate().queryForList("FeedMonitor.findAll");
  }
}