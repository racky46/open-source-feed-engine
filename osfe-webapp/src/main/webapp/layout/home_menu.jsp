<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<div id="menu">
<s:link beanclass="com.qagen.osfe.webapp.web.action.auth.HomeActionBean" event="managementFormsHome">
  Management Forms
</s:link> |
<s:link beanclass="com.qagen.osfe.webapp.web.action.auth.HomeActionBean" event="queryFormsHome">
  Query Forms
</s:link> |
<s:link beanclass="com.qagen.osfe.webapp.web.action.auth.HomeActionBean" event="schedulingFormsHome">
  Scheduling Forms
</s:link>
  </div>