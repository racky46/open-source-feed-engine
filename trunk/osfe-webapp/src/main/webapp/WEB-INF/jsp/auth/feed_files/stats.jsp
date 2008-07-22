<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table border="1">
  <thead>
    <tr>
      <th>Phase ID</th>
      <th>Avg Processing Time</th>
      <th>Total Time In MS</th>
      <th>Iteration Count</th>
    </tr>
  </thead>
  <c:forEach var="stat" items="${actionBean.statsList}">
    <tr>
      <td>${stat.phaseId}</td>
      <td>${stat.roundedAvgProcessingTime}</td>
      <td>${stat.totalTimeInMs}</td>
      <td>${stat.iterationCount}</td>
    </tr>
  </c:forEach>
</table>
