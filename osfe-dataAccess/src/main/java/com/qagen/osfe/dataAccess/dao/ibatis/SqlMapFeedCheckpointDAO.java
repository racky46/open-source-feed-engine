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

import com.qagen.osfe.dataAccess.dao.FeedCheckpointDAO;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapFeedCheckpointDAO extends SqlMapClientDaoSupport implements FeedCheckpointDAO {

  public void insert(FeedCheckpoint model) {
    getSqlMapClientTemplate().insert("FeedCheckPoint.insert", model);
  }

  public void update(FeedCheckpoint model) {
    getSqlMapClientTemplate().update("FeedCheckPoint.update", model);
  }

  public void delete(FeedCheckpoint model) {
    getSqlMapClientTemplate().delete("FeedCheckPoint.delete", model);
  }

  public FeedCheckpoint findByPrimaryId(Object primaryId) {
    return (FeedCheckpoint) getSqlMapClientTemplate().queryForObject("FeedCheckPoint.findByPrimaryId", primaryId);
  }

  public FeedCheckpoint findByFeedFileId(Integer feedFileId) {
    return (FeedCheckpoint) getSqlMapClientTemplate().queryForObject("FeedCheckPoint.findByFeedFileId", feedFileId);
  }
}