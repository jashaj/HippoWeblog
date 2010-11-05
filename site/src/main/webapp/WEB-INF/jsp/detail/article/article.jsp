<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<article>
  <section>
    <site:calendar doc="${document}" ispubdate="true"/>
    <h1>${document.title}</h1>
    <c:if test="${not empty document.summary}"><p>${document.summary}</p></c:if>
    <hst:html hippohtml="${document.html}" contentRewriter="${contentrewriter}"/>
  </section>
  
  <section class="share">
    <p><a class="a2a_dd" href="http://www.addtoany.com/share_save"><img src="http://static.addtoany.com/buttons/share_save_120_16.gif" width="120" height="16" alt="Share/Bookmark"/></a></p>
  </section>
  <hst:headContribution category="jsExternal"><script type="text/javascript" src="http://static.addtoany.com/menu/page.js"></script></hst:headContribution>
  
  <c:if test="${not empty comments and fn:length(comments)>0}">
    <section class="comments">
      <h2>${fn:length(comments)} <c:choose><c:when test="${fn:length(comments) eq 1}">comment</c:when><c:otherwise>comments</c:otherwise></c:choose> </h2>
      <c:forEach var="comment" items="${comments}">
        <div class="comment" id="${comment.name}">
          <c:choose>
            <c:when test="${not empty comment.website}"><h3><a href="${comment.website}" rel="external">${comment.person}</a> said</h3></c:when>
            <c:otherwise><h3>${comment.person} said</h3></c:otherwise>
          </c:choose>
          <p>${comment.summary}</p>
          <fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="commentdate"/>
          <p><time datetime="${commentdate}">${comment.printableDate}</time></p>
        </div>
      </c:forEach>
    </section>
  </c:if>
  <c:if test="${enableComments eq true}">
  <section>
    <hst:actionURL var="addURL">
        <hst:param name="type" value="add"/>
    </hst:actionURL>
    <form method="post" action="${addURL}" class="commentform" id="commentform">
      <fieldset>
        <legend>Leave your comment here</legend>
        <ul>
            <li><label for="person">Name: *</label><input type="text" id="person" name="person" required="required"/></li>
            <li><label for="email">Email:</label><input type="email" id="email" name="email"/> </li>
            <li><label for="website">Website:</label><input type="url" id="website" name="website"/> </li>
            <li><label for="comment">Comment: *</label><textarea name="comment" id="comment" rows="4" cols="40" required="required"></textarea></li>
            <li><input type="submit" value="Submit"/></li>
        </ul>
      </fieldset>
      <p>* required field</p>
    </form>  
  </section>
  </c:if>
</article>

<hst:headContribution>
  <title><c:out value="${document.rawTitle}" escapeXml="true"/> | jasha.eu</title>
</hst:headContribution>
