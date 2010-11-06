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
<article>
  <h1>Search result</h1>
  <c:choose>
    <c:when test="${not empty searchfor and nrhits eq 0}">
    <p>I'm sorry but I can't find any document that matches your question.</p>
    </c:when>
    <c:when test="${not empty searchfor}"><p>You searched for "${searchfor}" and I found ${nrhits} documents</p></c:when>
    <c:when test="${nrhits > 0}"><p>I found ${nrhits} documents</p></c:when>
    <c:otherwise>I'm sorry, did you ask something?</c:otherwise>
  </c:choose>
  <c:if test="${not empty documents}">
    <c:forEach var="doc" items="${documents}">
      <section class="teaser">
      
        <c:choose>
            <c:when test="${doc.type eq 'comment'}">
              <hst:link hippobean="${doc.commentTo}" var="hstlink"/>
              <c:set var="link" value="${hstlink}\#${doc.name}"/>
            </c:when>
            <c:otherwise>
              <hst:link hippobean="${doc}" var="link"/>
            </c:otherwise>
        </c:choose>
        <a href="${link}">
          <site:calendar doc="${doc}"/>
          <h2>${doc.title}</h2>
          <c:if test="${not empty doc.summary}"><p class="intro">${doc.summary}</p></c:if></a>
      </section>
    </c:forEach>
  </c:if>
  
  <c:if test="${pages > 1}">
  
  <ol class="paging">
  <c:forEach begin="0" end="${pages}" var="i">
  <hst:renderURL var="pageURL">
    <hst:param name="page" value="${i}"/>
    <hst:param name="searchfor" value="${searchfor}"/>
  </hst:renderURL>
  <c:choose>
    <c:when test="${i eq page}"><li>${i+1}</li></c:when>
    <c:otherwise><li><a href="${pageURL}">${i+1}</a></li></c:otherwise>
  </c:choose>
  </c:forEach>
  </ol>
  </c:if>
</article>

<hst:headContribution>
  <title>Search result | jasha.eu</title>
</hst:headContribution>
<hst:headContribution>
<meta name="robots" content="noindex, follow"/>
</hst:headContribution>