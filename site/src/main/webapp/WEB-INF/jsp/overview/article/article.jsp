<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<c:set var="title">Blogposts<c:if test="${not empty tag}">: <c:out value="${tag}" /></c:if></c:set>
<article>
  <h1>${title}</h1>
  <c:choose>
  <c:when test="${not empty documents}">
    <c:forEach var="doc" items="${documents}" varStatus="docstatus">
      <section class="teaser">
        <hst:link hippobean="${doc}" var="link"/>
          <a href="${link}"><site:calendar doc="${doc}"/>
          <h2>${doc.title}</h2>
          <c:if test="${not empty doc.overviewSummary}"><p class="intro">${doc.overviewSummary}</p></c:if></a>
          <c:if test="${not empty doc.tags and fn:length(doc.tags) > 0}">
            <p class="tags">Tags:
              <c:forEach items="${doc.tags}" var="localtag" varStatus="loop">${localtag}<c:if test="${not loop.last}">, </c:if></c:forEach>
            </p>
          </c:if>
      </section>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <p>I'm sorry but I couldn't find what you were looking for.</p>
  </c:otherwise>
  </c:choose>
  
  <c:if test="${pages > 1}">
  
  <ol class="paging">
  <c:forEach begin="0" end="${pages - 1}" var="i">
  <hst:renderURL var="pageURL">
    <hst:param name="page" value="${i}"/>
    <c:if test="${not empty tag}"><hst:param name="tag" value="${tag}"/></c:if>
  </hst:renderURL>
  <c:choose>
    <c:when test="${i eq page}"><li class="active">${i+1}</li></c:when>
    <c:otherwise><li><a href="${pageURL}">${i+1}</a></li></c:otherwise>
  </c:choose>
  </c:forEach>
  </ol>
  </c:if>
</article>

<hst:element name="title" var="titleElt">${title} | jasha.eu</hst:element>

<hst:headContribution element="${titleElt}"/>

<hst:headContribution><meta name="robots" content="noindex, follow"/></hst:headContribution>
<hst:headContribution>
  <c:choose>
    <c:when test="${not empty tag}">
      <meta content="Facet navigation results for ${tag}" name="description"/>
    </c:when>
    <c:otherwise>
      <meta content="Jasha Joachimsthal's website about technology, myself, my work at Hippo and other things that cross my mind" name="description"/>
    </c:otherwise>
  </c:choose>
</hst:headContribution>