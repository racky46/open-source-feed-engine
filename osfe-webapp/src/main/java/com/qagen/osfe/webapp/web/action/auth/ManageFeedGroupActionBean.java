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
import com.qagen.osfe.dataAccess.service.FeedGroupService;
import com.qagen.osfe.dataAccess.vo.FeedGroup;
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
 * Author: Philip Matheson
 * <p/>
 * This is the management class for Feed Group.  This action provides code
 * for listing all feed groups as well as general CRUD methods
 */
@UrlBinding("/action/feed/group/{$event}/{feedGroup.feedGroupId}/{page}/{rows}/{sidx}/{sord}")
public class ManageFeedGroupActionBean extends BaseActionBean {

    @ValidateNestedProperties({
    @Validate(field = "feedGroupId", required = true)
            })
    private FeedGroup feedGroup;
    private FeedGroupService feedGroupService;

    private Integer page = 1;
    private Integer rows = 10;
    private String sidx = "feedGroupId";
    private String sord = "asc";

    public FeedGroup getFeedGroup() {
        return feedGroup;
    }

    public void setFeedGroup(FeedGroup feedGroup) {
        this.feedGroup = feedGroup;
    }

    @SpringBean(FeedGroupService.SERVICE_ID)
    public void setDataSourceService(FeedGroupService feedGroupService) {
        this.feedGroupService = feedGroupService;
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
    @DontBind
    public Resolution display() {
        return new ForwardResolution(FEED_GROUP_LIST_VIEW);
    }

    @DontBind
    public Resolution add() {
        return new ForwardResolution(FEED_GROUP_MODIFY_VIEW);
    }

    @DontValidate
    public Resolution edit() {
        if (feedGroup.getFeedGroupId() != null) {
            setEditMode(true);
            feedGroup = feedGroupService.findByPrimaryId(feedGroup.getFeedGroupId());
        }
        return new ForwardResolution(FEED_GROUP_MODIFY_VIEW);
    }

    public Resolution save() {
        System.out.println(isEditMode());
        if (!isEditMode()) {
            feedGroupService.insert(feedGroup);
        } else {
            System.out.println("Updating");
            feedGroupService.update(feedGroup);
        }
        getContext().getMessages().add(new LocalizableMessage("feedGroupSaveSuccess"));
        return new RedirectResolution(ManageFeedGroupActionBean.class);
    }

    @DontValidate
    public Resolution delete() {
        feedGroupService.delete(feedGroup);
        return null;
    }

    @DontValidate
    public Resolution cancel() {
        return new RedirectResolution(ManageFeedGroupActionBean.class);
    }

    @DontValidate
    public Resolution list() {

        List<FeedGroup> feeds = feedGroupService.findAll();

        double val = 0;
        double totalPages = 0;
        if (feeds.size() >= new Double(rows)) {
            val = feeds.size() / new Double(rows);
            totalPages = Math.ceil(val);
        } else {
            totalPages = 1;
        }

        int start = rows * page - rows;
        feeds = feedGroupService.findAllWithLimit(new LimitParam(start, rows));

        JqGridJsonModel json = new JqGridJsonModel();
        json.setPage(String.valueOf(page));
        json.setRecords(String.valueOf(rows));
        json.setTotal(((int) totalPages));

        List<JqGridRow> rows = new ArrayList<JqGridRow>();
        for (FeedGroup ft : feeds) {
            JqGridRow row = new JqGridRow();
            row.setId(ft.getFeedGroupId());
            List<String> cells = new ArrayList<String>();
            cells.add(ft.getFeedGroupId());

            /* COULDN'T Figure out Java's ternary operator here...*/
            String concurrentRuns;
            String failedStateRuns;
            String phaseStats;
            if (ft.isAllowConcurrentRuns())
                concurrentRuns = "yes";
            else
                concurrentRuns = "no";

            if (ft.isAllowFailedStateRuns())
                failedStateRuns = "yes";
            else
                failedStateRuns = "no";

            if (ft.isCollectPhaseStats())
                phaseStats = "yes";
            else
                phaseStats = "no";

            cells.add(concurrentRuns);
            cells.add(failedStateRuns);
            cells.add(phaseStats);
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
