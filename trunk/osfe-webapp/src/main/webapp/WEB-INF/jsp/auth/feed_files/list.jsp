<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/query_forms.jsp" pageTitle="Feed Type List">
  <s:layout-component name="html-head">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jqGrid/themes/green/grid.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jqGrid/themes/jqModal.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/confirm.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/boxy.css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/js/jqModal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/js/jqDnR.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jqGrid/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery.simplemodal.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery.boxy.js"></script>

    

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/feed_file_list.js"></script>
  </s:layout-component>
  <s:layout-component name="contents">
    <s:messages/>
    <table id="feed_file_list" class="scroll" cellpadding="0" cellspacing="0"></table>
    <div id="pager" class="scroll" style="text-align:center;margin-bottom: 10px;"></div>

    <div id="detail_container" style="display:none;">
    <table id="feed_file_list_detail" class="scroll" cellpadding="0" cellspacing="0"></table>
    <div id="pager_detail" class="scroll" style="text-align:center;"></div>
    </div>
  </s:layout-component>
</s:layout-render>
