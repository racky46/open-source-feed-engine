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
package com.qagen.osfe.webapp.web.security;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.qagen.osfe.dataAccess.vo.FeedUser;
import com.qagen.osfe.webapp.web.action.LoginActionBean;
import com.qagen.osfe.webapp.web.context.OsfeActionBeanContext;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public class OsfeSecurityManager implements StripesSecurityManager {

  private ActionBeanContext context;

  public boolean isUserInRole(List<String> roles, ActionBeanContext context) {
    this.context = context;
    boolean isValid = false;
    FeedUser user = (FeedUser) context.getRequest().getSession().getAttribute(OsfeActionBeanContext.SESSION_FEED_USER);
    if (user == null) {
      return false;
    }
    for (String role : roles) {
      if (user.getFeedRole().getRoleName().equals(role)) {
        isValid = true;
      }
    }
    return isValid;
  }

  public boolean isUserInRole(List<String> roles, HttpServletRequest request, HttpServletResponse response) {
    boolean isValid = false;
    FeedUser user = (FeedUser) request.getSession().getAttribute(OsfeActionBeanContext.SESSION_FEED_USER);
    if (user == null) {
      return false;
    }
    for (String role : roles) {
      if (user.getFeedRole().getRoleName().equals(role)) {
        isValid = true;
      }
    }
    return isValid;
  }

  public Resolution getUnauthorizedResolution() {
    // TODO put some localized error message into flash scope for the login page
    context.getMessages().add(new LocalizableMessage("/action/login.roleNotAllowed"));
    
    return new RedirectResolution(LoginActionBean.class);
  }
}
