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

import com.qagen.osfe.dataAccess.dao.FeedDAO;
import com.qagen.osfe.dataAccess.vo.Feed;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapFeedDAO extends SqlMapClientDaoSupport implements FeedDAO {

  public void insert(Feed model) {
    getSqlMapClientTemplate().insert("Feed.insert", model);
  }

  public void update(Feed model) {
    getSqlMapClientTemplate().update("Feed.update", model);
  }

  public void delete(Feed model) {
    getSqlMapClientTemplate().delete("Feed.delete", model);
  }

  public Feed findByPrimaryId(Object primaryId) {
    return (Feed) getSqlMapClientTemplate().queryForObject("Feed.findByPrimaryId", primaryId);
  }
}
