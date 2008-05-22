<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-definition>
  <html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery-1.2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery.corner.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common.js"></script>

    <s:layout-component name="html-head">
       	${headerStuff}
    </s:layout-component>
  </head>
  <body>
    <s:layout-component name="header">
      <jsp:include page="_header.jsp" />
    </s:layout-component>
    <div id="content">
      <s:layout-component name="contents" />
    </div>
    <div id="footer" style="clear:both;">
      <s:layout-component name="footer">
        <jsp:include page="_footer.jsp" />
      </s:layout-component>
    </div>
  </body>
  </html>
</s:layout-definition>
