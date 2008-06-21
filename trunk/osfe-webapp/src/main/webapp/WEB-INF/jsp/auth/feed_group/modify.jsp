<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<s:layout-render name="/layout/default.jsp" pageTitle="Add">
  <s:layout-component name="contents">
    <div id="middleContents">
      <div class="outerBorder">
        <div style="color:white">Add/Update Feed Group</div>
        <div class="innerBorder">
          <s:errors/>
          <s:messages/>
          <s:form beanclass="com.qagen.osfe.webapp.web.action.auth.ManageFeedGroupActionBean">
            <table align="center">
              <tr>
                <td><s:label for="feedGroup.feedGroupId"/></td>
                <td>
                  <c:choose>
                    <c:when test="${!actionBean.editMode}">
                      <s:text name="feedGroup.feedGroupId"/>
                    </c:when>
                    <c:otherwise>
                      ${actionBean.feedGroup.feedGroupId}
                      <s:hidden name="feedGroup.feedGroupId" />
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <td><s:label for="feedGroup.allowConcurrentRuns" /></td>
                <td><s:checkbox name="feedGroup.allowConcurrentRuns"/></td>
              </tr>
              <tr>
                <td><s:label for="feedGroup.allowFailedStateRuns" /></td>
                <td><s:checkbox name="feedGroup.allowFailedStateRuns"/></td>
              </tr>
              <tr>
                <td><s:label for="feedGroup.collectPhaseStats" /></td>
                <td><s:checkbox name="feedGroup.collectPhaseStats"/></td>
              </tr>
              <tr>
                <td colspan="2" align="right">
                  <s:submit name="cancel" value="Cancel" />
                  <s:submit name="save" value="Save"/></td>
              </tr>
            </table>
            <s:hidden name="editMode" />
          </s:form>
        </div>
      </div>
    </div>
  </s:layout-component>
</s:layout-render>
