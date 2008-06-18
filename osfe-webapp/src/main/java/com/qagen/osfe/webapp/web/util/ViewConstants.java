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
package com.qagen.osfe.webapp.web.util;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public interface ViewConstants {

    public static final String JSP_PATH = "/WEB-INF/jsp/";
    public static final String INDEX_VIEW = JSP_PATH + "index.jsp";
    public static final String LOGIN_VIEW = JSP_PATH + "login.jsp";
    public static final String HOME_VIEW = JSP_PATH + "auth/home.jsp";
    public static final String FEED_DATASOURCE_LIST_VIEW = JSP_PATH + "auth/feed_datasource/list.jsp";
    public static final String FEED_DATASOURCE_MODIFY_VIEW = JSP_PATH + "auth/feed_datasource/modify.jsp";
    public static final String FEED_LIST_VIEW = JSP_PATH + "auth/feeds/list.jsp";
    public static final String FEED_MODIFY_VIEW = JSP_PATH + "auth/feeds/modify.jsp";
    public static final String FEED_TYPE_LIST_VIEW = JSP_PATH + "auth/feed_type/list.jsp";
    public static final String FEED_TYPE_MODIFY_VIEW = JSP_PATH + "auth/feed_type/modify.jsp";
    public static final String FEED_PROTOCOL_LIST_VIEW = JSP_PATH + "auth/feed_protocol/list.jsp";
    public static final String FEED_PROTOCOL_MODIFY_VIEW = JSP_PATH + "auth/feed_protocol/modify.jsp";

}
