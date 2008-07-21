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

import com.qagen.osfe.common.RoleConstants;
import com.qagen.osfe.dataAccess.param.LimitParam;
import com.qagen.osfe.dataAccess.service.FeedJobService;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import com.qagen.osfe.webapp.model.JqGridJsonModel;
import com.qagen.osfe.webapp.model.JqGridRow;
import com.qagen.osfe.webapp.web.action.BaseActionBean;
import com.qagen.osfe.webapp.web.security.Secure;
import flexjson.JSONSerializer;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
@UrlBinding("/action/feed/jobs/{$event}/{page}/{rows}/{sidx}/{sord}")
@Secure(roles = {RoleConstants.ADMINISTRATOR, RoleConstants.DATA_MANAGER, RoleConstants.FEED_MANAGER, RoleConstants.USER})
public class ViewFeedJobsActionBean extends BaseActionBean {

  private FeedJobService feedJobService;
  private Integer page = 1;
  private Integer rows = 10;
  private String sidx = "feedJobId";
  private String sord = "asc";

  @SpringBean(FeedJobService.SERVICE_ID)
  public void setFeedJobService(FeedJobService feedJobService) {
    this.feedJobService = feedJobService;
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

  public void setSord(String sord) {
    this.sord = sord;
  }

  @DefaultHandler
  @DontBind
  public Resolution display() {
    return new ForwardResolution(FEED_JOBS_VIEW);
  }

  @HttpCache(allow = false)
  public Resolution list() {
    List<FeedJob> feedJobList = feedJobService.findByActiveFailedJobStateId();

    double val = 0;
    double totalPages = 0;
    if (feedJobList.size() >= new Double(rows)) {
      val = feedJobList.size() / new Double(rows);
      totalPages = Math.ceil(val);
    } else {
      totalPages = 1;
    }

    int start = rows * page - rows;

    LimitParam limitParam = new LimitParam(start, rows);
    limitParam.setOrder(getSidx());
    limitParam.setSortOrder(getSord());
    feedJobList = feedJobService.findByActiveFailedJobStateIdWithLimit(limitParam);

    JqGridJsonModel json = new JqGridJsonModel();
    json.setPage(String.valueOf(page));
    json.setRecords(String.valueOf(rows));
    json.setTotal(((int) 1));

    List<JqGridRow> rows = new ArrayList<JqGridRow>();
    for (FeedJob feedJob : feedJobList) {
      JqGridRow row = new JqGridRow();
      row.setId(String.valueOf(feedJob.getFeedJobId()));
      List<String> cells = new ArrayList<String>();
      cells.add(String.valueOf(feedJob.getFeedJobId()));
      cells.add(String.valueOf(feedJob.getFeedFile().getFeedFileId()));
      cells.add(feedJob.getFeedFile().getFeed().getFeedId());
      cells.add(feedJob.getProcessingStart().toString());
      cells.add(feedJob.getFeedFile().getFeedFileName());
      cells.add(feedJob.getFeedJobState().getFeedJobStateId());
      row.setCell(cells);
      rows.add(row);
    }
    json.setRows(rows);

    JSONSerializer serializer = new JSONSerializer();
    String jsonResult = serializer.exclude("*.class").deepSerialize(json);
    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
  }
}
