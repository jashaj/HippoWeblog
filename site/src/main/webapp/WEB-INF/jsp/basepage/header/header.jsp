<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<%--
  * Copyright 2010 Jasha Joachimsthal
  *
  * Licensed under the Apache License, Version 2.0 (the  "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
--%>
<header>
  <div id="home"><a href="<hst:link path="/"/>">${labels['site.name']}</a></div>
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
