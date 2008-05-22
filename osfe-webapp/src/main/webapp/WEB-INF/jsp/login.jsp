<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
  <s:layout-component name="headerStuff">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/jquery.corner.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/login.js"></script>
  </s:layout-component>
  <s:layout-component name="contents">

    <div id="middleContents">
      <div id="errors">

      </div>

      <div id="loginContainer" class="outerBorder">

        <div style="color:white">LOGIN</div>
        <div id="innerContainer" class="innerBorder">
           <s:errors/>
          <s:form beanclass="com.qagen.osfe.webapp.web.action.LoginActionBean">
            <table align="center">
              <tr>
                <td><s:label for="user.username"/></td>
                <td><s:text name="user.username"/></td>
              </tr>
              <tr>
                <td><s:label for="user.password" /></td>
                <td><s:password name="user.password"/></td>
              </tr>
              <tr>
                <td colspan="2" align="right"><s:submit name="login" value="Login"/></td>
              </tr>
            </table>
          </s:form>
        </div>
      </div>
      
    </div>
  </s:layout-component>
</s:layout-render>