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