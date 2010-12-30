[#ftl]
[#assign hst=JspTaglibs["http://www.hippoecm.org/jsp/hst/core"]]
[#--
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
  --]
[#if facetnav?has_content && facetnav.folders?has_content]
<section>
  <h2>Blog archive</h2>
  [#list facetnav.folders as facet]
  <h3>${facet.name}</h3>
  [#if facet.folders?has_content]
    <ul class="facets">
      [#assign liclass][/#assign]
      [#list facet.folders as facetvalue]
      [#compress]
      [#if facetvalue_index = 5]<li class="less"><a href="#" class="toggle">&raquo; More</a></li>
        [#assign liclass] class="more"[/#assign]
      [/#if]
      <li${liclass}>
        [#assign facetlabel][#if facet.name = 'month']${labels['month.'+facetvalue.name]}[#else]${facetvalue.name}[/#if][/#assign]
        [#if facetvalue.leaf]${facetlabel} (${facetvalue.count})
          [#if facetvalue.count > 0]
            [#assign remove][#if facet.name = 'year'][@hst.link path="/blogposts"/][#else][@hst.facetnavigationlink current=facetnav remove=facetvalue/][/#if][/#assign]
            <a href="${remove}" class="deleteFacet" rel="nofollow">X</a>
          [/#if]
        [#else]<a href="[@hst.link hippobean=facetvalue/]" rel="nofollow"> ${facetlabel}</a> (${facetvalue.count})
        [/#if]
      </li>
      [#if facetvalue_has_next=false && facetvalue_index>4]<li class="more"><a href="#" class="toggle">&raquo; Less</a></li>[/#if]
      [/#compress]
      [/#list]
    </ul>
  [/#if]
  [/#list]
</section>
[/#if]
