<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"> 
<head>
  <hst:headContributions categoryExcludes="css,jsExternal,jsInline"/>
  <hst:headContributions categoryIncludes="css"/>
  <%-- Script for IE7+ to enable HTML5 elements --%>
  <!--[if gte IE 7]><script type="text/javascript">
(function(){var html5elmeents = "address|article|aside|audio|canvas|command|datalist|details|dialog|figure|figcaption|footer|header|hgroup|keygen|mark|meter|menu|nav|progress|ruby|section|time|video".split('|');
for(var i = 0; i < html5elmeents.length; i++){
document.createElement(html5elmeents[i]);
}})();</script><![endif]-->
</head>
<c:set var="bodyClass" value=""/>
<c:if test="${not empty cssClass}"><c:set var="bodyClass" value=" class=\"${cssClass}\""/></c:if>
<body${bodyClass}>
<c:set var="imagepath"><hst:link path="/images/dummy.jpg"/></c:set>
<c:set var="imagePath" value="${fn:substringBefore(imagepath,'dummy.jpg')}"/>
<%-- Popover div for IE6 and lower that this site needs a modern browser --%>
<!--[if lte IE 6]><script src="<hst:link path="/js/warning.js"/>"></script><script>window.onload=function(){e("<c:out value="${imagePath}"/>")}</script><![endif]-->
<div id="wrapper">
<hst:include ref="header"/>
<hst:include ref="article"/>
<hst:include ref="aside"/>
<hst:include ref="footer"/>
</div>
<script src="<hst:link path="/js/jquery-1.4.2.min.js"/>"></script>
<hst:headContributions categoryIncludes="jsExternal"/>
<hst:headContributions categoryIncludes="jsInline"/>
<%-- Google Analytics goes here --%>
</body>
</html>
