[#ftl]
[#assign hst=JspTaglibs["http://www.hippoecm.org/jsp/hst/core"]]
[#import "../../imports/imports.ftl" as site]
[#--
  * Copyright 2010-2011 Jasha Joachimsthal
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
<article>
  <h1>Search result</h1>
  [#if searchfor?has_content && nrhits = 0]<p>I'm sorry but I can't find any document that matches your question.</p>
  [#elseif searchfor?has_content]<p>You searched for "${searchfor}" and I found ${nrhits} documents</p>
  [#elseif nrhits gt 0]<p>I found ${nrhits} documents</p>
  [#else]<p>I'm sorry, did you ask something?</p>
  [/#if]

  [#if documents?has_content]
  [#list documents as doc]
  <section class="teaser">
    [#if doc.type="comment"]
    [#assign link][@hst.link hippobean=doc.commentTo/]#${doc.name}[/#assign]
    [#else][#assign link][@hst.link hippobean=doc/][/#assign]
    [/#if]
    <a href="${link}">[@site.calendar doc=doc/]
    <h2>${doc.title}</h2>
    [#if doc.summary?has_content]<p class="intro">${doc.summary}</p>[/#if]
    </a>
  </section>
  [/#list]
  [/#if]

  [#if pages gt 1]
    <ol class="paging">
    [#list 0..(pages - 1) as i]
    [#if i = page]<li class="active">${i+1}</li>
    [#else]
      [#assign pageURL][@hst.renderURL][@hst.param name="page" value="${i}"/]
      [#if searchfor?has_content][@hst.param name="searchfor" value="${searchfor}"/][/#if][/@hst.renderURL][/#assign]
      <li><a href="${pageURL}">${i+1}</a></li>[/#if]
    [/#list]
    </ol>
  [/#if]
</article>
[@hst.headContribution]<title>Search result | ${labels['site.name']}</title>[/@hst.headContribution]
[@hst.headContribution]<meta name="robots" content="noindex, follow"/>[/@hst.headContribution]
