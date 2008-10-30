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

import com.qagen.osfe.dataAccess.service.*;
import com.qagen.osfe.dataAccess.vo.*;
import com.qagen.osfe.webapp.model.JqGridJsonModel;
import com.qagen.osfe.webapp.model.JqGridRow;
import com.qagen.osfe.webapp.web.action.BaseActionBean;
import flexjson.JSONSerializer;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
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
@UrlBinding("/action/feeds/{$event}/{feed.feedId}")
public class ManageFeedsActionBean extends BaseActionBean {

  @ValidateNestedProperties({
  @Validate(field = "feedId", required = true),
  @Validate(field = "activationDate", required = true),
  @Validate(field = "terminationDate", required = true),
  @Validate(field = "lastSequenceNumber", required = true),
  @Validate(field = "maxConcurrentRuns", required = true)
    })

  private Feed feed;

  private FeedService feedService;
  private FeedDataSourceService feedDataSourceService;
  private FeedTypeService feedTypeService;
  private FeedProtocolService feedProtocolService;
  private FeedDirectionService feedDirectionService;
  private FeedGroupService feedGroupService;

  private List<FeedDataSource> dataSources;
  private List<FeedType> feedTypes;
  private List<FeedProtocol> feedProtocols;
  private List<FeedDirection> feedDirections;
  private List<FeedGroup> feedGroups;

  private Integer page = 1;
  private Integer rows = 10;
  private String sidx = "feed.feedId";
  private String sord = "asc";

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  @SpringBean(FeedService.SERVICE_ID)
  public void setFeedService(FeedService feedService) {
    this.feedService = feedService;
  }

  @SpringBean(FeedDataSourceService.SERVICE_ID)
  public void setFeedDataSourceService(FeedDataSourceService feedDataSourceService) {
    this.feedDataSourceService = feedDataSourceService;
  }

  @SpringBean(FeedTypeService.SERVICE_ID)
  public void setFeedTypeService(FeedTypeService feedTypeService) {
    this.feedTypeService = feedTypeService;
  }

  @SpringBean(FeedProtocolService.SERVICE_ID)
  public void setFeedProtocolService(FeedProtocolService feedProtocolService) {
    this.feedProtocolService = feedProtocolService;
  }

  @SpringBean(FeedDirectionService.SERVICE_ID)
  public void setFeedDirectionService(FeedDirectionService feedDirectionService) {
    this.feedDirectionService = feedDirectionService;
  }

  @SpringBean(FeedGroupService.SERVICE_ID)
  public void setFeedGroupService(FeedGroupService feedGroupService) {
    this.feedGroupService = feedGroupService;
  }

  public List<FeedDataSource> getDataSources() {
    return dataSources;
  }

  public void setDataSources(List<FeedDataSource> dataSources) {
    this.dataSources = dataSources;
  }

  public List<FeedType> getFeedTypes() {
    return feedTypes;
  }

  public void setFeedTypes(List<FeedType> feedTypes) {
    this.feedTypes = feedTypes;
  }

  public List<FeedProtocol> getFeedProtocols() {
    return feedProtocols;
  }

  public void setFeedProtocols(List<FeedProtocol> feedProtocols) {
    this.feedProtocols = feedProtocols;
  }

  public List<FeedDirection> getFeedDirections() {
    return feedDirections;
  }

  public void setFeedDirections(List<FeedDirection> feedDirections) {
    this.feedDirections = feedDirections;
  }

  public List<FeedGroup> getFeedGroups() {
    return feedGroups;
  }

  public void setFeedGroups(List<FeedGroup> feedGroups) {
    this.feedGroups = feedGroups;
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
    return new ForwardResolution(FEED_LIST_VIEW);
  }

  @DontBind
  public Resolution add() {
    return new ForwardResolution(FEED_MODIFY_VIEW);
  }

  public Resolution save() {
    if (!isEditMode()) {
      feedService.insert(feed);
    } else {
      feedService.update(feed);
    }
    return new RedirectResolution(ManageFeedsActionBean.class);
  }

  @DontValidate
  public Resolution edit() {
    if (feed.getFeedId() != null) {
      setEditMode(true);
      feed = feedService.findByPrimaryId(feed.getFeedId());
    }
    return new ForwardResolution(FEED_MODIFY_VIEW);
  }

  @DontValidate
  public Resolution delete() {
    feedService.delete(feed);
    return null;
  }

  @DontValidate
  public Resolution list() {
    final List<Feed> feeds = feedService.findAll();

    double val = 0;
    double totalPages = 0;

    if (feeds.size() >= new Double(rows)) {
      val = feeds.size() / new Double(rows);
      totalPages = Math.ceil(val);
    } else {
      totalPages = 1;
    }

    final JqGridJsonModel json = new JqGridJsonModel();

    json.setPage(String.valueOf(page));
    json.setRecords(String.valueOf(rows));
    json.setTotal(((int) totalPages));

    final List<JqGridRow> rows = new ArrayList<JqGridRow>();

    for (Feed feed : feeds) {
      final JqGridRow row = new JqGridRow();
      final List<String> cells = new ArrayList<String>();

      row.setId(feed.getFeedId());
      cells.add(feed.getFeedId());
      cells.add(feed.getFromDataSource().getFeedDataSourceId());
      cells.add(feed.getToDataSource().getFeedDataSourceId());
      cells.add(feed.getFeedType().getFeedTypeId());
      cells.add(feed.getFeedProtocol().getFeedProtocolId());
      cells.add(feed.getFeedDirection().getFeedDirectionId());
      if (feed.getFeedGroup() == null) {
        cells.add("n/a");
      } else {
        cells.add(feed.getFeedGroup().getFeedGroupId());
      }
      cells.add("action");
      row.setCell(cells);
      rows.add(row);
    }

    json.setRows(rows);

    final JSONSerializer serializer = new JSONSerializer();
    final String jsonResult = serializer.exclude("*.class").deepSerialize(json);

    return new StreamingResolution("text/javascript", new StringReader(jsonResult));
  }

  @After(on = {"add", "save", "edit"}, stages = LifecycleStage.BindingAndValidation)
  public void populateFormLists() {
    dataSources = feedDataSourceService.findAll();
    feedTypes = feedTypeService.findAll();
    feedProtocols = feedProtocolService.findAll();
    feedDirections = feedDirectionService.findAll();
    feedGroups = feedGroupService.findAll();
  }
}
