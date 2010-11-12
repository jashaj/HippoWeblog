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
<fmt:setLocale value="en_GB"/>
<section>
<c:if test="${not empty boxTitle}"><h2>${boxTitle}</h2></c:if>
    <c:choose>
        <c:when test="${not empty statuses}">
            <ul>
                <c:forEach items="${statuses}" var="status" begin="0" end="4">
                    <li>
                        <c:choose>
                            <c:when test="${status.retweet}">
                                RT ${status.retweetedStatus.user.screenName}: ${status.retweetedStatus.text}<br />
                                Retweeted at
                                <a href="http://twitter.com/${status.retweetedStatus.user.screenName}/status/${status.retweetedStatus.id}" rel="external">
                                    <time datetime="<fmt:formatDate value="${status.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>"><fmt:formatDate value="${status.createdAt}" pattern="d MMM H:mm"/></time></a>
                            </c:when>
                            <c:otherwise>
                                ${status.text}<br />
                                <a href="http://twitter.com/${status.user.screenName}/status/${status.id}" rel="external">
                                    <time datetime="<fmt:formatDate value="${status.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss"/>"><fmt:formatDate value="${status.createdAt}" pattern="d MMM H:mm"/></time></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>No tweets found</c:otherwise>
    </c:choose>
</section>
