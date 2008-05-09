package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: g
 * Date: May 9, 2008
 * Time: 2:08:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexActionBeanTest extends BaseActionBeanTest {

  @Test
  public void testDefaultHandler() throws Exception {
    MockServletContext context = getContext();

    MockRoundtrip trip = new MockRoundtrip(context, IndexActionBean.class);
    trip.execute();
    //IndexActionBean indexActionBean = trip.getActionBean(IndexActionBean.class);
    Assert.assertEquals(trip.getDestination(), "/WEB-INF/jsp/index.jsp");    
  }
}
