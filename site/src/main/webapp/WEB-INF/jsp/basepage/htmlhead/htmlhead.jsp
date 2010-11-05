<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<hst:headContribution><meta charset="UTF-8"/></hst:headContribution>

<hst:headContribution category="css">
  <link rel="stylesheet" href="<hst:link path="/css/style.css"/>" />
</hst:headContribution>
<hst:headContribution category="css">
  <link rel="stylesheet" href="<hst:link path="/css/jquery.fancybox-1.3.1.css"/>"/>
</hst:headContribution>
<hst:headContribution category="css">
  <link href="<hst:link path="/css/shMin.css"/>" rel="stylesheet" />
</hst:headContribution>
<hst:headContribution category="jsExternal">
  <script src="<hst:link path="/js/site.js"/>"></script>
</hst:headContribution>
<hst:headContribution category="jsExternal">
  <script src="<hst:link path="/js/jquery.fancybox-1.3.1.pack.js"/>"></script>
</hst:headContribution>
<hst:headContribution category="jsExternal">
  <script src="<hst:link path="/js/shMin.js"/>"></script>
</hst:headContribution>
<hst:headContribution category="jsInline">
  <script>init();</script>
</hst:headContribution>
<<hst:headContribution><link rel="shortcut icon" href="<hst:link path="/images/favicon.ico"/>" /></hst:headContribution>
<hst:headContribution><meta content="Jasha Joachimsthal" name="author"/></hst:headContribution> 
<hst:headContribution><meta content="Hippo Site Toolkit" name="generator"/></hst:headContribution> 
<hst:headContribution>
<link rel="alternate" type="application/atom+xml" title="Jasha&#39;s blog - Atom" href="<hst:link path="/feeds/posts/default"/>" />
</hst:headContribution>
<hst:headContribution>
<link rel="alternate" type="application/rss+xml" title="Jasha&#39;s blog - RSS" href="<hst:link path="/feeds/posts/default"/>?alt=rss" />
</hst:headContribution>