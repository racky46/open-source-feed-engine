package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.action.*;

/**
 * Created by IntelliJ IDEA.
 * User: g
 * Date: May 9, 2008
 * Time: 10:20:19 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseActionBean implements ActionBean {

  private ActionBeanContext context;

  public void setContext(ActionBeanContext actionBeanContext) {
    this.context = actionBeanContext;
  }

  public ActionBeanContext getContext() {
    return this.context;
  }

  @DefaultHandler
  @DontValidate
  public abstract Resolution display();
}
