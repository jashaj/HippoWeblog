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
<article>
  <section>
    <h1>${document.title}</h1>
    [#if document.summary?has_content]<p>${document.summary}</p>[/#if]
    [@hst.html hippohtml=document.html contentRewriter=contentrewriter/]
  </section>
</article>
[@hst.headContribution]<title>${document.rawTitle?xml} | ${labels['site.name']}</title>[/@hst.headContribution]
