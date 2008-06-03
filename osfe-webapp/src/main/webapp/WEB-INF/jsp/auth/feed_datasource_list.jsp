<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="html-head">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jqGrid/themes/basic/grid.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/feed_datasource_list.js"></script>
  </s:layout-component>
  <s:layout-component name="contents">
    <table id="feedDatasourceList" class="scroll" cellpadding="0" cellspacing="0" ></table>
    <div id="pager" class="scroll"></div>
  </s:layout-component>
</s:layout-render>
