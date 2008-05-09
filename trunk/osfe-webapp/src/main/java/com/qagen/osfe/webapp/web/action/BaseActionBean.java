package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.action.*;
import com.qagen.osfe.webapp.web.context.OsfeActionBeanContext;
import com.qagen.osfe.webapp.web.AbstractActionBeanContext;

/**
 * Created by IntelliJ IDEA.
 * User: g
 * Date: May 9, 2008
 * Time: 10:20:19 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseActionBean implements ActionBean {

  public static final String JSP_PATH = "/WEB-INF/jsp/";
  public static final String INDEX_VIEW = JSP_PATH + "index.jsp";

  private AbstractActionBeanContext context;

  public void setContext(ActionBeanContext actionBeanContext) {
    this.context = (AbstractActionBeanContext)actionBeanContext;
  }

  public ActionBeanContext getContext() {
    return this.context;
  }

  @DefaultHandler
  @DontValidate
  public abstract Resolution display();
}
