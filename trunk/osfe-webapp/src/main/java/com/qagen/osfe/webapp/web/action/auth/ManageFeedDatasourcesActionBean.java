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
package com.qagen.osfe.webapp.web.action.auth;

import com.qagen.osfe.dataAccess.param.LimitParam;
import com.qagen.osfe.dataAccess.service.FeedDataSourceService;
import com.qagen.osfe.dataAccess.vo.FeedDataSource;
import com.qagen.osfe.webapp.model.JqGridJsonModel;
import com.qagen.osfe.webapp.model.JqGridRow;
import com.qagen.osfe.webapp.web.action.BaseActionBean;
import flexjson.JSONSerializer;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Gregg Bolinger
 * <p/>
 * This is the management class for Feed Data Sources.  This action provides code
 * for listing all data sources as well as general CRUD methods
 */
@UrlBinding("/action/feed/datasources/{$event}/{dataSource.feedDataSourceId}/{page}/{rows}/{sidx}/{sord}")
public class ManageFeedDatasourcesActionBean extends BaseActionBean {

  @ValidateNestedProperties({
  @Validate(field = "feedDataSourceId", required = true),
  @Validate(field = "description", required = true)
      })
  private FeedDataSource dataSource;
  private FeedDataSourceService dataSourceService;

  private Integer page = 1;
  private Integer rows = 10;
  private String sidx = "feedDataSourceId";
  private String sord = "asc";

  public FeedDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(FeedDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @SpringBean(FeedDataSourceService.SERVICE_ID)
  public void setDataSourceService(FeedDataSourceService dataSourceService) {
    this.dataSourceService = dataSourceService;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getRows() {
    return rows;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }

  public String getSidx() {
    return sidx;
  }

  public void setSidx(String sidx) {
    this.sidx = sidx;
  }

  public String getSord() {
    return sord;
  }

  public void setSord(String sortd) {
    this.sord = sortd;
  }

  @DefaultHandler
  @DontValidate
  public Resolution display() {
    return new ForwardResolution(FEED_DATASOURCE_LIST_VIEW);
  }

  @DontBind
  public Resolution add() {
    return new ForwardResolution(FEED_DATASOURCE_MODIFY_VIEW);
  }

  @DontValidate
  public Resolution edit() {
    if (dataSource.getFeedDataSourceId() != null) {
      setEditMode(true);
      dataSource = dataSourceService.findByPrimaryId(dataSource.getFeedDataSourceId());
    }
    return new ForwardResolution(FEED_DATASOURCE_MODIFY_VIEW);
  }

  public Resolution save() {
    System.out.println(isEditMode());
    if (!isEditMode()) {
      dataSourceService.insert(dataSource);
    } else {
      System.out.println("Updating");
      dataSourceService.update(dataSource);
    }
    getContext().getMessages().add(new LocalizableMessage("feedDataSourceSaveSuccess"));
    return new RedirectResolution(ManageFeedDatasourcesActionBean.class);
  }

  @DontValidate
  public Resolution delete() {
    dataSourceService.delete(dataSource);
    return null;
  }

  @DontValidate
  public Resolution cancel() {
    return new RedirectResolution(ManageFeedDatasourcesActionBean.class);
  }

  @DontValidate
  public Resolution list() {

    List<FeedDataSource> feeds = dataSourceService.findAll();

    double val = 0;
    double totalPages = 0;
    if (feeds.size() >= new Double(rows)) {
      val = feeds.size() / new Double(rows);
      totalPages = Math.ceil(val);
    } else {
      totalPages = 1;
    }

    int start = rows*page - rows;
    feeds = dataSourceService.findAllWithLimit(new LimitParam(start, rows));

    JqGridJsonModel json = new JqGridJsonModel();
    json.setPage(String.valueOf(page));
    json.setRecords(String.valueOf(rows));
    json.setTotal(((int) totalPages));

    List<JqGridRow> rows = new ArrayList<JqGridRow>();
    for (FeedDataSource fds : feeds) {
      JqGridRow row = new JqGridRow();
      row.setId(fds.getFeedDataSourceId());
      List<String> cells = new ArrayList<String>();
      cells.add(fds.getFeedDataSourceId());
      cells.add(fds.getDescription());
      cells.add("action");
      row.setCell(cells);
      rows.add(row);
    }
    json.setRows(rows);

    JSONSerializer serializer = new JSONSerializer();
    String jsonResult = serializer.exclude("*.class").deepSerialize(json);
    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
  }
}
