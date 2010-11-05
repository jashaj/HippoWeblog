<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<aside>
  <c:forEach var="include" items="${includes}">
    <hst:include ref="${include}" />
  </c:forEach>
</aside>
