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
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="document" type="org.onehippo.forge.weblogdemo.beans.BaseDocument" %>
<%@ attribute name="contentrewriter" type="org.hippoecm.hst.content.rewriter.ContentRewriter" %>
<%@ attribute name="titleaslink" type="java.lang.Boolean" required="false"
              description="If set to true, the title is clickable" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<section>
  <site:calendar doc="${document}" ispubdate="true"/>
  <c:choose>
    <c:when test="${titleaslink eq true}">
      <h1><a href="<hst:link hippobean="${document}"/>">${document.title}</a></h1>
    </c:when>
    <c:otherwise><h1>${document.title}</h1></c:otherwise>
  </c:choose>
  <c:if test="${not empty document.summary}"><p>${document.summary}</p></c:if>
  <hst:html hippohtml="${document.html}" contentRewriter="${contentrewriter}"/>
</section>
