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
import com.qagen.osfe.dataAccess.service.FeedFileService;
import com.qagen.osfe.dataAccess.service.FeedFileStateService;
import com.qagen.osfe.dataAccess.service.FeedJobService;
import com.qagen.osfe.dataAccess.service.FeedPhaseStatsService;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedFileState;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import com.qagen.osfe.dataAccess.vo.FeedPhaseStats;
import com.qagen.osfe.webapp.model.JqGridJsonModel;
import com.qagen.osfe.webapp.model.JqGridRow;
import com.qagen.osfe.webapp.web.action.BaseActionBean;
import com.qagen.osfe.webapp.web.security.Secure;
import flexjson.JSONSerializer;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
@UrlBinding("/action/feed/files/{$event}/{feedFile.feedFileId}/{page}/{rows}/{sidx}/{sord}/")
@Secure(roles = {RoleConstants.ADMINISTRATOR, RoleConstants.DATA_MANAGER, RoleConstants.FEED_MANAGER, RoleConstants.USER})
public class ViewFeedFileDetailsActionBean extends BaseActionBean {

  private FeedFile feedFile;
  private FeedFileService feedFileService;
  private FeedJobService feedJobService;
  private FeedPhaseStatsService statsService;
  private FeedFileStateService stateService;
  private Integer page = 1;
  private Integer rows = 10;
  private String sidx = "feedJobId";
  private String sord = "asc";
  private List<FeedFileState> stateList;
  private Date dateFilter;
  private String stateFilter;

  public FeedFile getFeedFile() {
    return feedFile;
  }

  public void setFeedFile(FeedFile feedFile) {
    this.feedFile = feedFile;
  }

  @SpringBean(FeedFileService.SERVICE_ID)
  public void setFeedFileService(FeedFileService feedFileService) {
    this.feedFileService = feedFileService;
  }

  @SpringBean(FeedJobService.SERVICE_ID)
  public void setFeedJobService(FeedJobService feedJobService) {
    this.feedJobService = feedJobService;
  }

  @SpringBean(FeedPhaseStatsService.SERVICE_ID)
  public void setStatsService(FeedPhaseStatsService statsService) {
    this.statsService = statsService;
  }

  @SpringBean(FeedFileStateService.SERVICE_ID)
  public void setStateService(FeedFileStateService stateService) {
    this.stateService = stateService;
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

  public List<FeedFileState> getStateList() {
    return stateList;
  }

  public Date getDateFilter() {
    return dateFilter;
  }

  public void setDateFilter(Date dateFilter) {
    this.dateFilter = dateFilter;
  }

  public String getStateFilter() {
    return stateFilter;
  }

  public void setStateFilter(String stateFilter) {
    this.stateFilter = stateFilter;
  }

  @DefaultHandler
  @DontBind
  public Resolution display() {
    return new ForwardResolution(FEED_FILE_VIEW);
  }

  public Resolution list() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    List<FeedFile> feedFileList = feedFileService.findAll();

//    double val = 0;
//    double totalPages = 0;
//    if (feedJobList.size() >= new Double(rows)) {
//      val = feedJobList.size() / new Double(rows);
//      totalPages = Math.ceil(val);
//    } else {
//      totalPages = 1;
//    }
//
//    int start = rows * page - rows;
//
//    LimitParam limitParam = new LimitParam(start, rows);
//    limitParam.setOrder(getSidx());
//    limitParam.setSortOrder(getSord());
//    feedJobList = feedJobService.findByActiveFailedJobStateIdWithLimit(limitParam);

    JqGridJsonModel json = new JqGridJsonModel();
    json.setPage(String.valueOf(1));
    json.setRecords(String.valueOf(10));
    json.setTotal(((int) 10));

    List<JqGridRow> rows = new ArrayList<JqGridRow>();
    for (FeedFile feedFile : feedFileList) {
      JqGridRow row = new JqGridRow();
      row.setId(String.valueOf(feedFile.getFeedFileId()));
      List<String> cells = new ArrayList<String>();
      cells.add(String.valueOf(feedFile.getFeedFileId()));
      cells.add(String.valueOf(feedFile.getFeed().getFeedId()));
      cells.add(feedFile.getFeedFileName());
      cells.add(sdf.format(feedFile.getFeedFileDate()));
      cells.add(feedFile.getFeedFileTime().toString());
      cells.add(feedFile.getFeedFileState().getFeedFileStateId());
      cells.add("<a href='/app/action/feed/files/stats/" + feedFile.getFeedFileId() + "' rel='stats'>View Stats</a>");
      row.setCell(cells);
      rows.add(row);
    }
    json.setRows(rows);

    JSONSerializer serializer = new JSONSerializer();
    String jsonResult = serializer.exclude("*.class").deepSerialize(json);
    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
  }

  public Resolution details() {
    //List<FeedJob> feedJobList = feedJobService.findByFeedFileId(feedJob.getFeedFile().getFeedFileId());

    /*double val = 0;
    double totalPages = 0;
    if (feedJobList.size() >= new Double(rows)) {
      val = feedJobList.size() / new Double(rows);
      totalPages = Math.ceil(val);
    } else {
      totalPages = 1;
    }

    int start = rows * page - rows;
*/
    //LimitParam limitParam = new LimitParam(start, rows);
    //limitParam.setOrder(getSidx());
    //limitParam.setSortOrder(getSord());
    if (feedFile.getFeedFileId() > 0) {
      List<FeedJob> feedJobList = feedJobService.findByFeedFileId(feedFile.getFeedFileId());

      JqGridJsonModel json = new JqGridJsonModel();
      json.setPage(String.valueOf(1));
      json.setRecords(String.valueOf(10));
      json.setTotal(((int) 10));

      List<JqGridRow> rows = new ArrayList<JqGridRow>();
      for (FeedJob feedJob : feedJobList) {
        JqGridRow row = new JqGridRow();
        row.setId(String.valueOf(feedJob.getFeedJobId()));
        List<String> cells = new ArrayList<String>();
        cells.add(String.valueOf(feedJob.getFeedJobId()));


        cells.add(feedJob.getFeedJobState().getFeedJobStateId());
        cells.add(feedJob.getProcessingStart().toString());
        cells.add(feedJob.getProcessingEnd().toString());
        cells.add(feedJob.getFailureCode());
        cells.add(String.valueOf(feedJob.getFailedRowNumber()));
        cells.add(feedJob.getFailureMessage());
        row.setCell(cells);
        rows.add(row);
      }
      json.setRows(rows);

      JSONSerializer serializer = new JSONSerializer();
      String jsonResult = serializer.exclude("*.class").deepSerialize(json);
      return new StreamingResolution("text/javascript", new StringReader(jsonResult));
    } else {
      JqGridJsonModel json = new JqGridJsonModel();
      json.setPage(String.valueOf(1));
      json.setRecords(String.valueOf(10));
      json.setTotal(((int) 10));
      List<JqGridRow> rows = new ArrayList<JqGridRow>();

      JqGridRow row = new JqGridRow();
      row.setId(" ");
      List<String> cells = new ArrayList<String>();

      cells.add(" ");
      cells.add(" ");
      cells.add(" ");
      cells.add(" ");
      cells.add(" ");
      cells.add(" ");
      cells.add(" ");
      row.setCell(cells);
      rows.add(row);

      json.setRows(rows);

      JSONSerializer serializer = new JSONSerializer();
      String jsonResult = serializer.exclude("*.class").deepSerialize(json);
      return new StreamingResolution("text/javascript", new StringReader(jsonResult));
    }
  }

  public Resolution stats() {
    return new ForwardResolution("/WEB-INF/jsp/auth/feed_files/stats.jsp");
  }

  public Resolution filterResults() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    if (stateFilter.equalsIgnoreCase("ALL") && dateFilter == null) {
      return list();
    }
    if (dateFilter == null) {

    List<FeedFile> feedFileList = feedFileService.findByFeedFileState(stateFilter);
    JqGridJsonModel json = new JqGridJsonModel();
    json.setPage(String.valueOf(1));
    json.setRecords(String.valueOf(10));
    json.setTotal(((int) 10));

    List<JqGridRow> rows = new ArrayList<JqGridRow>();
    for (FeedFile feedFile : feedFileList) {
      JqGridRow row = new JqGridRow();
      row.setId(String.valueOf(feedFile.getFeedFileId()));
      List<String> cells = new ArrayList<String>();
      cells.add(String.valueOf(feedFile.getFeedFileId()));
      cells.add(String.valueOf(feedFile.getFeed().getFeedId()));
      cells.add(feedFile.getFeedFileName());
      cells.add(sdf.format(feedFile.getFeedFileDate()));
      cells.add(feedFile.getFeedFileTime().toString());
      cells.add(feedFile.getFeedFileState().getFeedFileStateId());
      cells.add("<a href='/app/action/feed/files/stats/" + feedFile.getFeedFileId() + "' rel='stats'>View Stats</a>");
      row.setCell(cells);
      rows.add(row);
    }
    json.setRows(rows);

    JSONSerializer serializer = new JSONSerializer();
    String jsonResult = serializer.exclude("*.class").deepSerialize(json);
    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
    }else{
      return null;
    }
  }






  public List<FeedPhaseStats> getStatsList() {
    return statsService.findByFeedFileId(feedFile.getFeedFileId());
  }

  @After(stages = {LifecycleStage.BindingAndValidation})
  public void initLists() {
    this.stateList = stateService.findAll();
  }
}
