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

import com.qagen.osfe.dataAccess.dao.FeedQueueDAO;
import com.qagen.osfe.dataAccess.param.MonitorQueueParam;
import com.qagen.osfe.dataAccess.vo.FeedQueue;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedQueueDAO extends SqlMapClientDaoSupport implements FeedQueueDAO {

  public void insert(FeedQueue model) {
    getSqlMapClientTemplate().insert("FeedQueue.insert", model);
  }

  public void update(FeedQueue model) {
    getSqlMapClientTemplate().update("FeedQueue.update", model);
  }

  public void delete(FeedQueue model) {
    getSqlMapClientTemplate().delete("FeedQueue.delete", model);
  }

  public FeedQueue findByPrimaryId(Object primaryId) {
    return (FeedQueue) getSqlMapClientTemplate().queryForObject("FeedQueue.findByPrimaryId", primaryId);
  }

  @SuppressWarnings("unchecked")
  public List<FeedQueue> findAll(String monitorId) {
    return getSqlMapClientTemplate().queryForList("FeedQueue.findAll", monitorId);
  }

  public void deleteAll(String monitorId) {
    getSqlMapClientTemplate().delete("FeedQueue.deleteAll", monitorId);
  }

  public void deleteByMonitorIdQueueId(MonitorQueueParam param) {
    getSqlMapClientTemplate().delete("FeedQueue.deleteByMonitorIdQueueId", param);
  }

  @SuppressWarnings("unchecked")
  public List<FeedQueue> findByMonitorIdQueueId(MonitorQueueParam param) {
    return getSqlMapClientTemplate().queryForList("FeedQueue.findByMonitorIdQueueId", param);
  }
}
