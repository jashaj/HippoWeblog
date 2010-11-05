<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<article>
  <section>
    <h1>${document.title}</h1>
    <c:if test="${not empty document.summary}"><p>${document.summary}</p></c:if>
    <hst:html hippohtml="${document.html}" contentRewriter="${contentrewriter}"/>
  </section>
</article>

<hst:headContribution>
  <title>${document.title} | jasha.eu</title>
</hst:headContribution>