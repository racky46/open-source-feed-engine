<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<s:layout-render name="/layout/management_forms.jsp" pageTitle="Add">
<s:layout-component name="html-head">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/ui.datepicker.css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery/ui.datepicker.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/modify_feed.js"></script>
</s:layout-component>

  <s:layout-component name="contents">
    <div id="middleContents">
      <div class="outerBorder">
        <div style="color:white">Add/Update Feed</div>
        <div class="innerBorder">
          <s:errors/>
          <s:messages/>
          <s:form beanclass="com.qagen.osfe.webapp.web.action.auth.ManageFeedsActionBean">
            <table cellpadding="2" cellspacing="0">
              <tr>
                <td><s:label for="feed.feedId"/></td>
                <td>
                  <c:choose>
                    <c:when test="${!actionBean.editMode}">
                      <s:text name="feed.feedId"/>
                    </c:when>
                    <c:otherwise>
                      ${actionBean.feed.feedId}
                      <s:hidden name="feed.feedId" />
                    </c:otherwise>
                  </c:choose>
                  </td>
              </tr>
              <tr>
                <td><s:label for="feed.activationDate"/></td>
                <td><s:text id="feed.activationDate" name="feed.activationDate"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.terminationDate"/></td>
                <td><s:text id="feed.terminationDate" name="feed.terminationDate"/></td>

              </tr>
              <tr>
                <td><s:label for="feed.allowConcurrentRuns"/></td>
                <td><s:checkbox name="feed.allowConcurrentRuns"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.allowFailedStateRuns"/></td>
                <td><s:checkbox name="feed.allowFailedStateRuns"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.restartAtCheckpoint"/></td>
                <td><s:checkbox name="feed.restartAtCheckpoint"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.collectPhaseStats"/></td>
                <td><s:checkbox name="feed.collectPhaseStats"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.lastSequenceNumber"/></td>
                <td><s:text name="feed.lastSequenceNumber"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.maxConcurrentRuns"/></td>
                <td><s:text name="feed.maxConcurrentRuns"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.feedDirectory"/></td>
                <td><s:text class="wide" name="feed.feedDirectory"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.feedDocument"/></td>
                <td><s:text class="wide" name="feed.feedDocument"/></td>
              </tr>
              <tr>
                <td><s:label for="feed.fromDataSource"/> </td>
                <td><s:select name="feed.fromDataSource.feedDataSourceId" >
                      <s:options-collection collection="${actionBean.dataSources}" value="feedDataSourceId" label="feedDataSourceId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td><s:label for="feed.toDataSource"/> </td>
                <td><s:select name="feed.toDataSource.feedDataSourceId" >
                      <s:options-collection collection="${actionBean.dataSources}" value="feedDataSourceId" label="feedDataSourceId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td><s:label for="feed.feedType"/> </td>
                <td><s:select name="feed.feedType.feedTypeId" >
                      <s:options-collection collection="${actionBean.feedTypes}" value="feedTypeId" label="feedTypeId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td><s:label for="feed.feedProtocol"/> </td>
                <td><s:select name="feed.feedProtocol.feedProtocolId" >
                      <s:options-collection collection="${actionBean.feedProtocols}" value="feedProtocolId" label="feedProtocolId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td><s:label for="feed.feedDirection"/> </td>
                <td><s:select name="feed.feedDirection.feedDirectionId" >
                      <s:options-collection collection="${actionBean.feedDirections}" value="feedDirectionId" label="feedDirectionId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td><s:label for="feed.feedGroup"/> </td>
                <td><s:select name="feed.feedGroup.feedGroupId" >
                      <s:options-collection collection="${actionBean.feedGroups}" value="feedGroupId" label="feedGroupId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td><s:label for="feed.feedQueueType"/> </td>
                <td><s:select name="feed.feedQueueType.feedQueueTypeId" >
                      <s:options-collection collection="${actionBean.feedQueueTypes}" value="feedQueueTypeId" label="feedQueueTypeId" />
                    </s:select>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td align="right">
                  <s:submit name="cancel" value="Cancel" />
                  <s:submit name="save" value="Save"/>
                </td>
              </tr>
            </table>
            <s:hidden name="editMode"/>
          </s:form>
        </div>
      </div>
    </div>
  </s:layout-component>
</s:layout-render>
