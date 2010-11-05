<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<c:if test="${not empty feed}">
  <section>
    <c:if test="${not empty boxTitle}"><h2>${boxTitle}</h2></c:if>
    <ul>
      <c:forEach items="${feed.entries}" var="entry" begin="0" end="9">
        <li>
          <c:if test="${not empty entry.authors && entry.authors[0].name ne '(author unknown)'}">
            <c:out value="${entry.authors[0].name}: "/>
          </c:if>
          <c:choose>
            <c:when test="${showLinks eq true}">
              <a href="${entry.link}" rel="external">${entry.title}</a>
            </c:when>
            <c:otherwise>${entry.title}</c:otherwise>
          </c:choose>
        </li>
      </c:forEach>
    </ul>
  </section>
</c:if>