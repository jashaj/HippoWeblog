<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="doc" description="Document" required="true" type="org.onehippo.weblogdemo.beans.BaseDocument" %>
<%@ attribute name="ispubdate" description="If true then the time element will get the attribute pubdate" required="false" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<fmt:formatDate value="${doc.date}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="docdate"/>
<div class="date"><time datetime="${docdate}"<c:if test="${ispubdate eq 'true'}"> pubdate="pubdate"</c:if>><span class="month">${doc.monthOfDate}</span> <span class="day">${doc.dayOfDate}</span> <span class="year">${doc.yearOfDate}</span></time></div>