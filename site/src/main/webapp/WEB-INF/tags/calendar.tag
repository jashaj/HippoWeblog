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
<%@ attribute name="doc" description="Document" required="true" type="org.onehippo.forge.weblogdemo.beans.BaseDocument" %>
<%@ attribute name="ispubdate" description="If true then the time element will get the attribute pubdate" required="false" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
<fmt:formatDate value="${doc.date}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="docdate"/>
<div class="date"><time datetime="${docdate}"<c:if test="${ispubdate eq 'true'}"> pubdate="pubdate"</c:if>><span class="month">${doc.monthOfDate}</span> <span class="day">${doc.dayOfDate}</span> <span class="year">${doc.yearOfDate}</span></time></div>