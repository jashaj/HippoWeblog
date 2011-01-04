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
<header>
  <div id="home"><a href="[@hst.link path="/"/]">${labels['site.name']}</a></div>
  [#if menu??]
  <nav>
    <ul>
    [#list menu.siteMenuItems as menuItem]
      [#assign menulink][#if menuItem.externalLink?has_content]${menuItem.externalLink}[#elseif menuItem.hstLink?has_content][@hst.link link=menuItem.hstLink/][#else]#[/#if][/#assign]
      [#assign liclass][#if (menuItem.expanded || menuItem.selected)] class="active"[/#if][/#assign]
      [#compress]<li${liclass}><a href="${menulink}">${menuItem.name}</a></li>[/#compress]
    [/#list]
    </ul>
  </nav>
  [/#if]

  <form method="get" action="[@hst.link path="/search"/]">
    <fieldset>
      <legend>Search form</legend>
      <label for="searchfor">Search for</label>
      [#assign value][#if searchfor?has_content]value="${searchfor}"[/#if][/#assign]
      <input type="search" id="searchfor" name="searchfor" ${value} placeholder="Search my site"/>
      <input type="submit" value="Search"/>
    </fieldset>
  </form>
</header>
