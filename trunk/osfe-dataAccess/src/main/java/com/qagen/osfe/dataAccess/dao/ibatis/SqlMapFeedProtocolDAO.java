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

import com.qagen.osfe.dataAccess.dao.FeedProtocolDAO;
import com.qagen.osfe.dataAccess.vo.FeedProtocol;
import com.qagen.osfe.dataAccess.param.LimitParam;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class SqlMapFeedProtocolDAO extends SqlMapClientDaoSupport implements FeedProtocolDAO {

    public void insert(FeedProtocol model) {
        getSqlMapClientTemplate().insert("FeedProtocol.insert", model);
    }

    public void update(FeedProtocol model) {
        getSqlMapClientTemplate().update("FeedProtocol.update", model);
    }

    public void delete(FeedProtocol model) {
        getSqlMapClientTemplate().delete("FeedProtocol.delete", model);
    }

    public FeedProtocol findByPrimaryId(Object primaryId) {
        return (FeedProtocol) getSqlMapClientTemplate().queryForObject("FeedProtocol.findByPrimaryId", primaryId);
    }

    @SuppressWarnings("unchecked")
    public List<FeedProtocol> findAll() {
        return getSqlMapClientTemplate().queryForList("FeedProtocol.findAll");
    }

    @SuppressWarnings("unchecked")
    public List<FeedProtocol> findAllWithLimit(LimitParam limits) {
        return getSqlMapClientTemplate().queryForList("FeedProtocol.findAllWithLimit", limits);

    }
}
