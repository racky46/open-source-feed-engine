package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.controller.DispatcherServlet;
import org.testng.annotations.BeforeTest;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: g
 * Date: May 9, 2008
 * Time: 2:50:16 PM
 * To change this template use File | Settings | File Templates.
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
