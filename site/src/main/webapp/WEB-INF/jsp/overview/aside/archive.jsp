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
<c:if test="${not empty facetnav and not empty facetnav.folders}">
<section>
  <h2>Blog archive</h2>
  
  <c:forEach var="facet" items="${facetnav.folders}">
    <h3>${facet.name}</h3>
    <c:if test="${not empty facet.folders}">
      <ul class="facets">
        <c:forEach var="facetvalue" items="${facet.folders}" begin="0" end="4">
          <li>
             <c:choose>
              <c:when test="${facet.name eq 'month'}">
                <c:set var="labelkey" value="month.${facetvalue.name}" />
                <c:set var="facetlabel" value="${labels[labelkey]}"/>
              </c:when>
              <c:otherwise>
                <c:set var="facetlabel" value="${facetvalue.name}"/>
              </c:otherwise>
             </c:choose>
             <c:choose>
               <c:when test="${facetvalue.leaf}">
                  <c:out value="${facetlabel}" escapeXml="true"/>
                   (${facetvalue.count})
                  <c:if test="${facetvalue.count > 0}">
                      <c:choose>
                        <c:when test="${facet.name eq 'year' }">
                            <hst:link var="remove" path="/blogposts"/>
                        </c:when>
                        <c:otherwise><hst:facetnavigationlink var="remove" current="${facetnav}" remove="${facetvalue}"/></c:otherwise>
                      </c:choose>
                      [<a href="${remove}" class="deleteFacet" rel="nofollow">X</a>]
                  </c:if>
               </c:when>
               <c:otherwise>
                 <hst:link var="link" hippobean="${facetvalue}"/>
                 <a href="${link}" rel="nofollow"><c:out value="${facetlabel}" escapeXml="true"/></a>
                   (${facetvalue.count})
               </c:otherwise>
             </c:choose>
          </li>
        </c:forEach>
        <c:if test="${fn:length(facet.folders)>5}">
          <li class="less"><a href="#" class="toggle">&raquo; More</a></li>
  
          <c:forEach var="facetvalue" items="${facet.folders}" begin="5">
              <li class="more">
                 <c:choose>
                  <c:when test="${facet.name eq 'month'}">
                    <c:set var="labelkey" value="month.${facetvalue.name}" />
                    <c:set var="facetlabel" value="${labels[labelkey]}"/>
                  </c:when>
                  <c:otherwise>
                    <c:set var="facetlabel" value="${facetvalue.name}"/>
                  </c:otherwise>
                 </c:choose>
                 <c:choose>
                   <c:when test="${facetvalue.leaf}">
                      <c:out value="${facetlabel}" escapeXml="true"/>
                       (${facetvalue.count})
                      <c:if test="${facetvalue.count > 0}">
                        <c:choose>
                          <c:when test="${facet.name eq 'year' }">
                              <hst:link var="remove" path="/blogposts"/>
                          </c:when>
                          <c:otherwise><hst:facetnavigationlink var="remove" current="${facetnav}" remove="${facetvalue}"/></c:otherwise>
                        </c:choose>
                        [<a href="${remove}" class="deleteFacet" rel="nofollow">X</a>]
                      </c:if>
                   </c:when>
                   <c:otherwise>
                     <hst:link var="link" hippobean="${facetvalue}"/>
                     <a href="${link}" rel="nofollow"><c:out value="${facetlabel}" escapeXml="true"/></a>
                       (${facetvalue.count})
                   </c:otherwise>
                 </c:choose>
              </li>
            </c:forEach>
            <li class="more"><a href="#" class="toggle">&raquo; Less</a></li>
        </c:if>
      </ul>
    </c:if>
  </c:forEach>
</section>
</c:if>
