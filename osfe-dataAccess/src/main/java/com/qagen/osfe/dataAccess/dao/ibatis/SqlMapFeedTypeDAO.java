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

import com.qagen.osfe.dataAccess.dao.FeedTypeDAO;
import com.qagen.osfe.dataAccess.vo.FeedType;
import com.qagen.osfe.dataAccess.param.LimitParam;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedTypeDAO extends SqlMapClientDaoSupport implements FeedTypeDAO {

    public void insert(FeedType model) {
        getSqlMapClientTemplate().insert("FeedType.insert", model);
    }

    public void update(FeedType model) {
        getSqlMapClientTemplate().update("FeedType.update", model);
    }

    public void delete(FeedType model) {
        getSqlMapClientTemplate().delete("FeedType.delete", model);
    }

    public FeedType findByPrimaryId(Object primaryId) {
        return (FeedType) getSqlMapClientTemplate().queryForObject("FeedType.findByPrimaryId", primaryId);
    }

    @SuppressWarnings("unchecked")
    public List<FeedType> findAll() {
        return getSqlMapClientTemplate().queryForList("FeedType.findAll");
    }

    @SuppressWarnings("unchecked")
    public List<FeedType> findAllWithLimit(LimitParam limits) {
        return getSqlMapClientTemplate().queryForList("FeedType.findAllWithLimit", limits);

    }
}
