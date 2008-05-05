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
package com.qagen.osfe.dataAccess.service.impl;

import com.qagen.osfe.dataAccess.dao.DataSourceDAO;
import com.qagen.osfe.dataAccess.service.DataSourceService;
import com.qagen.osfe.dataAccess.vo.DataSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DataSourceServiceImpl implements DataSourceService {
  private DataSourceDAO dataSourceDAO;

  public void setDataSourceDAO(DataSourceDAO dataSourceDAO) {
    this.dataSourceDAO = dataSourceDAO;
  }

  @Transactional
  public void insert(DataSource model) {
    dataSourceDAO.insert(model);
  }

  @Transactional
  public void update(DataSource model) {
    dataSourceDAO.update(model);
  }

  @Transactional
  public void delete(DataSource model) {
    dataSourceDAO.delete(model);
  }

  @Transactional
  public DataSource findByPrimaryId(Integer primaryId) {
    return dataSourceDAO.findByPrimaryId(primaryId);
  }

  @Transactional
  public List<DataSource> findAll() {
    return dataSourceDAO.findAll();
  }
}
