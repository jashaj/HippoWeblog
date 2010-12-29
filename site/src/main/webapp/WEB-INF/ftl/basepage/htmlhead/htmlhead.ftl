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
[@hst.headContribution category="css"]
  <link rel="stylesheet" href="[@hst.link path="/css/style.css"/]" />
[/@hst.headContribution]
[@hst.headContribution category="css"]
  <link rel="stylesheet" href="[@hst.link path="/css/jquery.fancybox-1.3.1.css"/]"/>
[/@hst.headContribution]
[@hst.headContribution category="css"]
  <link href="[@hst.link path="/css/shMin.css"/]" rel="stylesheet" />
[/@hst.headContribution]
[@hst.headContribution category="jsExternal"]
  <script src="[@hst.link path="/js/site.js"/]"></script>
[/@hst.headContribution]
[@hst.headContribution category="jsExternal"]
  <script src="[@hst.link path="/js/jquery.fancybox-1.3.1.pack.js"/]"></script>
[/@hst.headContribution]
[@hst.headContribution category="jsExternal"]
  <script src="[@hst.link path="/js/shMin.js"/]"></script>
[/@hst.headContribution]
[@hst.headContribution category="jsInline"]
  <script>init();</script>
[/@hst.headContribution]
<[@hst.headContribution]<link rel="shortcut icon" href="[@hst.link path="/images/favicon.ico"/]" />[/@hst.headContribution]
[@hst.headContribution]<meta content="${labels['site.author']}" name="author"/>[/@hst.headContribution]
[@hst.headContribution]<meta content="Hippo Site Toolkit" name="generator"/>[/@hst.headContribution]
[@hst.headContribution]<meta name="viewport" content="width=device-width, initial-scale=1.0"/>[/@hst.headContribution]
[@hst.headContribution]<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />[/@hst.headContribution]
[@hst.headContribution]
<link rel="alternate" type="application/atom+xml" title="${labels['site.name']} - Atom" href="[@hst.link path="/feeds/posts/default"/]" />
[/@hst.headContribution]
[@hst.headContribution]
<link rel="alternate" type="application/rss+xml" title="${labels['site.name']} - RSS" href="[@hst.link path="/feeds/posts/default"/]?alt=rss" />
[/@hst.headContribution]