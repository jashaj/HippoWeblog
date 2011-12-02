[#ftl]
[#assign hst=JspTaglibs["http://www.hippoecm.org/jsp/hst/core"]]
<!doctype html>
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
<!--[if lt IE 7]><html class="no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]><html class="no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]><html class="no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--><html class="no-js" lang="en"> <!--<![endif]-->
<head>
<meta charset="UTF-8"/>
[@hst.headContributions categoryExcludes="css,jsExternal,jsInline"/]
[@hst.headContributions categoryIncludes="css"/]
<script src="[@hst.link path="/js/libs/modernizr-2.0.6.min.js"/]"></script>
</head>
<body>
<div id="container">
[@hst.include ref="header"/]
<div id="main" role="main">
    <article class="mainarticle">
    [@hst.include ref="article"/]
    </article>
[@hst.include ref="aside"/]
    <div class="clearfix"></div>
</div>
[@hst.include ref="footer"/]
</div>
<script src="//ajax.aspnetcdn.com/ajax/jQuery/jquery-1.6.2.min.js"></script>
<script>!window.jQuery && document.write(unescape('%3Cscript src="[@hst.link path="/js/libs/jquery-1.6.2-min.js"/]"%3E%3C/script%3E'))</script>
<script src="[@hst.link path="/js/plugins.js"/]"></script>

[@hst.headContributions categoryIncludes="jsExternal"/]
[@hst.headContributions categoryIncludes="jsInline"/]
[#-- Google Analytics goes here --]
[#if labels['googleanalytics.account']?has_content]
<script>if (window.location.hostname!="localhost") {
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', '${labels['googleanalytics.account']}']);
    [#if labels['googleanalytics.domain']?has_content]
    _gaq.push(['_setDomainName', '${labels['googleanalytics.domain']}']);
    [/#if]
    _gaq.push(['_trackPageview']);
    (function() {
      var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
      ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
      var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();
}</script>
[/#if]
</body>
</html>