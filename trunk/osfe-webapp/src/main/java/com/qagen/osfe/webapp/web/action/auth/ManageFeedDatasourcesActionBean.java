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
 */
@UrlBinding("/action/feed/datasources/{$event}/{page}/{rows}/{sidx}/{sord}")
public class ManageFeedDatasourcesActionBean extends BaseActionBean {

  @ValidateNestedProperties({
  @Validate(field = "feedDataSourceId", required = true),
  @Validate(field = "description", required = true)
      })
  private FeedDataSource dataSource;

  private FeedDataSourceService dataSourceService;


  private String page = "1";
  private String rows = "10";
  private String sidx = "";
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

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getRows() {
    return rows;
  }

  public void setRows(String rows) {
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

  public Resolution save() {
    dataSourceService.insert(dataSource);
    getContext().getMessages().add(new LocalizableMessage("feedDataSourceSaveSuccess"));
    return new RedirectResolution(ManageFeedDatasourcesActionBean.class);
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
      totalPages = Math.round(val);
    } else {
      totalPages = 1;
    }

    JqGridJsonModel json = new JqGridJsonModel();
    json.setPage(page);
    json.setRecords(rows);
    json.setTotal(((int) totalPages));

    List<JqGridRow> rows = new ArrayList<JqGridRow>();
    for (FeedDataSource fds : feeds) {
      JqGridRow row = new JqGridRow();
      row.setId(fds.getFeedDataSourceId());
      List<String> cells = new ArrayList<String>();
      cells.add(fds.getFeedDataSourceId());
      cells.add(fds.getDescription());
      StringBuilder actions = new StringBuilder();
      actions.append("<a href='#' title='delete' id='").append(fds.getFeedDataSourceId()).append("'>Delete</a><br/>");
      actions.append("<a href='#' title='edit' id='").append(fds.getFeedDataSourceId()).append("'>Edit</a>");
      cells.add(actions.toString());
      row.setCell(cells);
      rows.add(row);
    }
    json.setRows(rows);

    JSONSerializer serializer = new JSONSerializer();
    String jsonResult = serializer.exclude("*.class").deepSerialize(json);
    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
  }
}
