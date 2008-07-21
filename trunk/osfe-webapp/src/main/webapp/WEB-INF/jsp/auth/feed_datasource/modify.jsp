<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<s:layout-render name="/layout/management_forms.jsp" pageTitle="Add" beanclass="com.qagen.osfe.webapp.web.action.auth.ManageFeedDatasourcesActionBean">
  <s:layout-component name="contents">
    <div id="middleContents">
      <div class="outerBorder">
        <div style="color:white">Add/Update Feed Datasource</div>
        <div class="innerBorder">
          <s:errors/>
          <s:messages/>
          <s:form beanclass="com.qagen.osfe.webapp.web.action.auth.ManageFeedDatasourcesActionBean">
            <table align="center">
              <tr>
                <td><s:label for="dataSource.feedDataSourceId"/></td>
                <td>
                  <c:choose>
                    <c:when test="${!actionBean.editMode}">
                      <s:text name="dataSource.feedDataSourceId"/>
                    </c:when>
                    <c:otherwise>
                      ${actionBean.dataSource.feedDataSourceId}
                      <s:hidden name="dataSource.feedDataSourceId" />
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
              <tr>
                <td><s:label for="dataSource.description" /></td>
                <td><s:textarea name="dataSource.description"/></td>
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
