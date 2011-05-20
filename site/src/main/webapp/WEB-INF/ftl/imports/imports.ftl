[#ftl]
[#assign hst=JspTaglibs["http://www.hippoecm.org/jsp/hst/core"]]
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
[#macro blog document titleaslink=false]
<section>
  [@calendar doc=document ispubdate=true/]
  [#if titleaslink=true]<h1><a href="[@hst.link hippobean=document/]">${document.title}</a></h1>
  [#else]<h1>${document.title}</h1>
  [/#if]
  [#if document.summary?has_content]<p>${document.summary}</p>[/#if]
  [#assign bodytext][@hst.html hippohtml=document.html contentRewriter=contentrewriter/][/#assign]
  [#if bodytext?has_content]${bodytext}[/#if]

</section>
[/#macro]

[#macro calendar doc ispubdate=false]
<div class="date">
[@compress single_line=true]
  <time datetime="${doc.date?string("yyyy-MM-dd'T'HH:mm:ss")}" [#if ispubdate=true] pubdate="pubdate"[/#if]>
  <span class="month">${doc.monthOfDate}</span> <span class="day">${doc.dayOfDate}</span> <span class="year">${doc.yearOfDate}</span>
  </time>
[/@compress]
</div>
[/#macro]
