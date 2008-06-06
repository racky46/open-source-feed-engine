<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp" pageTitle="Data Source List">
  <s:layout-component name="html-head">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jqGrid/themes/green/grid.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jqGrid/themes/jqModal.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/confirm.css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/js/jqModal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/js/jqDnR.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery.simplemodal.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/feed_list.js"></script>
  </s:layout-component>
  <s:layout-component name="contents">
    <s:messages/>
    <table id="feedList" class="scroll" cellpadding="0" cellspacing="0"></table>
    <div id="pager" class="scroll" style="text-align:center;margin-bottom: 5px;"></div>
    <s:link beanclass="com.qagen.osfe.webapp.web.action.auth.ManageFeedsActionBean" event="add">
      <img border="0" align="left" src="${pageContext.request.contextPath}/images/row_add.gif" alt="Add Row" />
      Add New Feed
    </s:link>
    <div id='confirm' style='display:none'>
	    <a href='#' title='Close' class='modalCloseX modalClose'>x</a>
	    <div class='header'><span>Confirm</span></div>
	    <p class='message'></p>
	    <div class='buttons'>
	    <div class='no modalClose'>No</div><div class='yes'>Yes</div>
    </div>
  </s:layout-component>
</s:layout-render>
