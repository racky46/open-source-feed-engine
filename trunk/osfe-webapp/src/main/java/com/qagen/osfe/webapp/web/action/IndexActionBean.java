package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * Created by IntelliJ IDEA.
 * User: g
 * Date: May 9, 2008
 * Time: 10:21:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexActionBean extends BaseActionBean {

  @Override
  public Resolution display() {
    return new ForwardResolution("/WEB-INF/jsp/index.jsp");
  }
}
