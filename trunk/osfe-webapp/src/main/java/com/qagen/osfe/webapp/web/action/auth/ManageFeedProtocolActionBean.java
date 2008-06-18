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

import com.qagen.osfe.webapp.web.action.BaseActionBean;
import com.qagen.osfe.webapp.model.JqGridJsonModel;
import com.qagen.osfe.webapp.model.JqGridRow;
import com.qagen.osfe.dataAccess.vo.FeedProtocol;
import com.qagen.osfe.dataAccess.service.FeedProtocolService;
import com.qagen.osfe.dataAccess.param.LimitParam;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.Validate;

import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;

import flexjson.JSONSerializer;

/**
 * Author: Philip Matheson
 * <p/>
 * This is the management class for Feed Protocol.  This action provides code
 * for listing all feed protcols as well as general CRUD methods
 */
@UrlBinding("/action/feed/protocol/{$event}/{feedProtocol.feedProtocolId}/{page}/{rows}/{sidx}/{sord}")
public class ManageFeedProtocolActionBean extends BaseActionBean {

    @ValidateNestedProperties({
    @Validate(field = "feedProtocolId", required = true),
    @Validate(field = "description", required = true)
            })
    private FeedProtocol feedProtocol;
    private FeedProtocolService feedProtocolService;

    private Integer page = 1;
    private Integer rows = 10;
    private String sidx = "feedProtocolId";
    private String sord = "asc";

    public FeedProtocol getFeedProtocol() {
        return feedProtocol;
    }

    public void setFeedProtocol(FeedProtocol feedProtocol) {
        this.feedProtocol = feedProtocol;
    }

    @SpringBean(FeedProtocolService.SERVICE_ID)
    public void setDataSourceService(FeedProtocolService feedProtocolService) {
        this.feedProtocolService = feedProtocolService;
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
        return new ForwardResolution(FEED_PROTOCOL_LIST_VIEW);
    }

    @DontBind
    public Resolution add() {
        return new ForwardResolution(FEED_PROTOCOL_MODIFY_VIEW);
    }

    @DontValidate
    public Resolution edit() {
        if (feedProtocol.getFeedProtocolId() != null) {
            setEditMode(true);
            feedProtocol = feedProtocolService.findByPrimaryId(feedProtocol.getFeedProtocolId());
        }
        return new ForwardResolution(FEED_PROTOCOL_MODIFY_VIEW);
    }

    public Resolution save() {
        System.out.println(isEditMode());
        if (!isEditMode()) {
            feedProtocolService.insert(feedProtocol);
        } else {
            System.out.println("Updating");
            feedProtocolService.update(feedProtocol);
        }
        getContext().getMessages().add(new LocalizableMessage("feedProtocolSaveSuccess"));
        return new RedirectResolution(ManageFeedProtocolActionBean.class);
    }

    @DontValidate
    public Resolution delete() {
        feedProtocolService.delete(feedProtocol);
        return null;
    }

    @DontValidate
    public Resolution cancel() {
        return new RedirectResolution(ManageFeedProtocolActionBean.class);
    }

    @DontValidate
    public Resolution list() {

        List<FeedProtocol> feeds = feedProtocolService.findAll();

        double val = 0;
        double totalPages = 0;
        if (feeds.size() >= new Double(rows)) {
            val = feeds.size() / new Double(rows);
            totalPages = Math.ceil(val);
        } else {
            totalPages = 1;
        }

        int start = rows * page - rows;
        feeds = feedProtocolService.findAllWithLimit(new LimitParam(start, rows));

        JqGridJsonModel json = new JqGridJsonModel();
        json.setPage(String.valueOf(page));
        json.setRecords(String.valueOf(rows));
        json.setTotal(((int) totalPages));

        List<JqGridRow> rows = new ArrayList<JqGridRow>();
        for (FeedProtocol ft : feeds) {
            JqGridRow row = new JqGridRow();
            row.setId(ft.getFeedProtocolId());
            List<String> cells = new ArrayList<String>();
            cells.add(ft.getFeedProtocolId());
            cells.add(ft.getDescription());
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
