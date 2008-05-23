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
package com.qagen.osfe.webapp.web.tag;

import com.qagen.osfe.webapp.web.security.StripesSecurityManager;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.StripesFilter;

import java.util.List;
import java.util.Arrays;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
public class SecureTag extends TagSupport {

  private String roles;
  //private static Log log = Log.getInstance(SecureTag.class);


  public SecureTag() {
    super();
    initValues();
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public int doStartTag() throws JspException {
    final StripesSecurityManager securityManager = getSecurityManager();

    if (roles == null || roles.length() < 1) {
      return SKIP_BODY;
    }
    List<String> anyRoles = Arrays.asList(roles.trim().split(","));
    if (securityManager.isUserInRole(anyRoles, ((HttpServletRequest) pageContext.getRequest()), ((HttpServletResponse) pageContext.getResponse()))) {
      return EVAL_BODY_INCLUDE;
    }
    return SKIP_BODY;

  }

  private void initValues() {
    roles = "";
  }

  private StripesSecurityManager getSecurityManager() {
    final Configuration config = StripesFilter.getConfiguration();
    final String sm = config.getBootstrapPropertyResolver().getProperty("StripesSecurityManager.class");
    if (sm != null) {
      try {
        Class clazz = Class.forName(sm.trim());
        return (StripesSecurityManager) clazz.newInstance();
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return null;
      } catch (InstantiationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return null;
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return null;
      }


    }
    return null;
  }

}
