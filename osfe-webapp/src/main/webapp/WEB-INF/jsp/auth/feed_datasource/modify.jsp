<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
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
                <td><s:text name="dataSource.feedDataSourceId"/></td>
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
          </s:form>
        </div>
      </div>
    </div>
  </s:layout-component>
</s:layout-render>
