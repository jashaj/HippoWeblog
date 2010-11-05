<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<header>
  <div id="home"><a href="<hst:link path="/"/>">jasha.eu</a> <span class="beta">beta</span></div>
  <c:if test="${not empty menu}">
    <nav>
      <ul>
      <c:forEach items="${menu.siteMenuItems}" var="menuItem">
        <c:set var="menulink" value="#"/>
        <c:choose>
          <c:when test="${not empty menuItem.externalLink}">
            <c:set var="menulink" value="${menuItem.externalLink}"/>
          </c:when>
          <c:when test="${not empty menuItem.hstLink}">
            <hst:link link="${menuItem.hstLink}" var="hstLink"/>
            <c:set var="menulink" value="${hstLink}"/>
          </c:when>
        </c:choose>
        <c:set var="liclass" value=""/>
          <c:if test="${menuItem.expanded or menuItem.selected}">
            <c:set var="liclass" value=" class=\"active\""/>
          </c:if>
        <li${liclass}><a href="${menulink}">${menuItem.name}</a></li>
      </c:forEach>
      </ul>
    </nav>
  </c:if>

  <form method="get" action="<hst:link path="/search"/>">
    <fieldset>
      <legend>Search form</legend>
      <label for="searchfor">Search for</label>
      <c:if test="${not empty searchfor}"><c:set var="value" value="value=\"${searchfor}\"" /></c:if>
      <input type="search" id="searchfor" name="searchfor" ${value} placeholder="Search my site"/>
      <input type="submit" value="Search"/>
    </fieldset>
  </form>
</header>
