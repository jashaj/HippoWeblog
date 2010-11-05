<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
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
