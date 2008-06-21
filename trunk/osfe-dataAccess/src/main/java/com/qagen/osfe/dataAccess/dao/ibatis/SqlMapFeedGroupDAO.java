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

import com.qagen.osfe.dataAccess.dao.FeedGroupDAO;
import com.qagen.osfe.dataAccess.vo.FeedGroup;
import com.qagen.osfe.dataAccess.param.LimitParam;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedGroupDAO extends SqlMapClientDaoSupport implements FeedGroupDAO {

    public void insert(FeedGroup model) {
        getSqlMapClientTemplate().insert("FeedGroup.insert", model);
    }

    public void update(FeedGroup model) {
        getSqlMapClientTemplate().update("FeedGroup.update", model);
    }

    public void delete(FeedGroup model) {
        getSqlMapClientTemplate().delete("FeedGroup.delete", model);
    }

    public FeedGroup findByPrimaryId(Object primaryId) {
        return (FeedGroup) getSqlMapClientTemplate().queryForObject("FeedGroup.findByPrimaryId", primaryId);
    }

    @SuppressWarnings("unchecked")
    public List<FeedGroup> findAll() {
        return getSqlMapClientTemplate().queryForList("FeedGroup.findAll");
    }

    @SuppressWarnings("unchecked")
    public List<FeedGroup> findAllWithLimit(LimitParam limits) {
        return getSqlMapClientTemplate().queryForList("FeedGroup.findAllWithLimit", limits);
    }
}
