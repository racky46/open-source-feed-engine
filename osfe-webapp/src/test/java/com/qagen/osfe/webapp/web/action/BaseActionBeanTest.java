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
package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.controller.DispatcherServlet;
import org.testng.annotations.BeforeTest;

import java.util.Map;
import java.util.HashMap;

/**
 * Author: Gregg Bolinger
 * <p>
 */
public class BaseActionBeanTest {

  private MockServletContext context;

  @BeforeTest
  public void setup() {
    System.out.println("SETTING UP");
    context = new MockServletContext("test");
    Map<String, String> filterParams = new HashMap<String, String>();
    filterParams.put("ActionResolver.Packages", "com.qagen.osfe.webapp.web.action");
    filterParams.put("ActionBeanContext.Class", "com.qagen.osfe.webapp.web.context.OsfeTestActionBeanContext");
    context.addFilter(StripesFilter.class, "StripesFilter", filterParams);

    context.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
  }

  public MockServletContext getContext() {
    return this.context;
  }
}
