[#ftl]
[#assign hst=JspTaglibs["http://www.hippoecm.org/jsp/hst/core"]]
[#import "../../imports/imports.ftl" as site]
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

[#assign title]${pageTitle}[#if tag?has_content]: ${tag}[/#if][/#assign]
[@hst.headContribution]<title>${title} | ${labels['site.name']}</title>[/@hst.headContribution]
[@hst.headContribution]<meta name="robots" content="noindex, follow"/>[/@hst.headContribution]
<article>
  <h1>${title}</h1>
  [#if documents?has_content]
    [#list documents as doc]
    <section class="teaser">
      <a href="[@hst.link hippobean=doc/]">[@site.calendar doc=doc/]
        <h2>${doc.title}</h2>
        [#if doc.overviewSummary?has_content]<p class="intro">${doc.overviewSummary}</p>[/#if]
        [#if doc.tags?has_content]
        <p class="tags">Tags:
          [#list doc.tags as localtag]${localtag}[#if localtag_has_next], [/#if][/#list]
        </p>
        [/#if]
      </a>
    </section>
    [/#list]
  [#else]<p>I'm sorry but I couldn't find what you were looking for.</p>
  [/#if]
  [#if pages gt 1]
    <ol class="paging">
    [#list 0..(pages - 1) as i]
    [#if i = page]<li class="active">${i+1}</li>
    [#else]
      [#assign pageURL][@hst.renderURL][@hst.param name="page" value="${i+1}"/]
      [#if tag?has_content][@hst.param name="tag" value="${tag}"/][/#if][/@hst.renderURL][/#assign]
      <li><a href="${pageURL}">${i+1}</a></li>[/#if]
    [/#list]
    </ol>
  [/#if]
</article>
