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

import com.qagen.osfe.dataAccess.vo.FeedDataSource;
import com.qagen.osfe.webapp.model.JqGridJsonModel;
import com.qagen.osfe.webapp.model.JqGridRow;
import com.qagen.osfe.webapp.web.action.BaseActionBean;
import flexjson.JSONSerializer;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
@UrlBinding("/action/feed/datasources/{$event}/{page}/{rows}/{sidx}/{sord}")
public class ManageFeedDatasourcesActionBean extends BaseActionBean {

  private String page = "1";
  private String rows = "10";
  private String sidx = "";
  private String sord = "asc";

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

  @Override
  public Resolution display() {
    return new ForwardResolution(FEED_DATASOURCE_LIST_VIEW);
  }

  public Resolution list() {

    // This would generally be a simple service call to get all the
    // datasources.  Faked here for ease of explination
    List<FeedDataSource> feeds = new ArrayList<FeedDataSource>();
    for (int index = 0; index < 55; index++) {
      FeedDataSource fds = new FeedDataSource();

      fds.setFeedDataSourceId("ID " + index);
      fds.setDescription("Desc " + index);
      feeds.add(fds);
    }
    // End fake service call

    double val = 0;
    double totalPages = 0;
    if (feeds.size() >= new Double(rows)) {
      val = feeds.size() / new Double(rows);
      totalPages = Math.round(val);
    }else{
        totalPages = 1;
    }

    JqGridJsonModel json = new JqGridJsonModel();
    json.setPage(page);
    json.setRecords(rows);
    json.setTotal(((int)totalPages));

    System.out.println((int)totalPages);


    List<JqGridRow> rows = new ArrayList<JqGridRow>();
    for (FeedDataSource fds : feeds) {
      JqGridRow row = new JqGridRow();
      row.setId(fds.getFeedDataSourceId());
      List<String> cells = new ArrayList<String>();
      cells.add(fds.getFeedDataSourceId());
      cells.add(fds.getDescription());
      cells.add("<a title='delete' id='" + fds.getFeedDataSourceId() + "'>Delete</div>");
      row.setCell(cells);
      rows.add(row);
    }
    json.setRows(rows);

    JSONSerializer serializer = new JSONSerializer();
    String jsonResult = serializer.exclude("*.class").deepSerialize(json);
    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
  }
}
