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

import com.qagen.osfe.dataAccess.dao.CheckPointDAO;
import com.qagen.osfe.dataAccess.vo.Checkpoint;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapCheckPointDAO extends SqlMapClientDaoSupport implements CheckPointDAO {

  public void insert(Checkpoint model) {
    getSqlMapClientTemplate().insert("CheckPoint.insert", model);
  }

  public void update(Checkpoint model) {
    getSqlMapClientTemplate().update("CheckPoint.update", model);
  }

  public void delete(Checkpoint model) {
    getSqlMapClientTemplate().delete("CheckPoint.delete", model);
  }

  public Checkpoint findByPrimaryId(Object primaryId) {
    return (Checkpoint) getSqlMapClientTemplate().queryForObject("CheckPoint.findByPrimaryId", primaryId);
  }

  public Checkpoint findByFeedFileId(Integer feedFileId) {
    return (Checkpoint) getSqlMapClientTemplate().queryForObject("CheckPoint.findByFeedFileId", feedFileId);
  }
}