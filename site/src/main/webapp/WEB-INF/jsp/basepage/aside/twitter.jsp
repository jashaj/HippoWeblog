<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<c:if test="${not empty statuses}">
<fmt:setLocale value="en_GB"/>
  <section>
    <c:if test="${not empty boxTitle}"><h2>${boxTitle}</h2></c:if>
    <ul>
      <c:forEach items="${statuses}" var="status" begin="0" end="4">
        <li>
          <c:choose>
            <c:when test="${status.retweet}">
              RT ${status.retweetedStatus.user.screenName}: ${status.retweetedStatus.text}<br />
              Retweeted at 
              <a href="http://twitter.com/${status.retweetedStatus.user.screenName}/status/${status.retweetedStatus.id}" rel="external">
              <time datetime="<fmt:formatDate value="${status.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>"><fmt:formatDate value="${status.createdAt}" pattern="d MMM H:mm"/></time></a>
            </c:when>
            <c:otherwise>
              ${status.text}<br />
              <a href="http://twitter.com/${status.user.screenName}/status/${status.id}" rel="external">
              <time datetime="<fmt:formatDate value="${status.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>"><fmt:formatDate value="${status.createdAt}" pattern="d MMM H:mm"/></time></a>
            </c:otherwise>
          </c:choose>
        </li>
      </c:forEach>
    </ul>
  </section>
</c:if>