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
[@hst.headContribution]<title>${document.rawTitle?xml} | ${labels['site.name']}</title>[/@hst.headContribution]

<article>
[@site.blog document=document/]
    <section class="share">
    <p><a class="a2a_dd" href="http://www.addtoany.com/share_save"><img src="http://static.addtoany.com/buttons/share_save_120_16.gif" width="120" height="16" alt="Share/Bookmark"/></a></p>
  </section>
  [@hst.headContribution category="jsExternal"]<script type="text/javascript" src="http://static.addtoany.com/menu/page.js"></script>[/@hst.headContribution]

  [#if comments?? && comments?has_content]
    <h2>${comments?size} [#if comments?size=1]comment[#else]comments[/#if]</h2>
    [#list comments as comment]
    <div class="comment" id="${comment.name}">
      [#if comment.website?has_content]<h3><a href="${comment.website}" rel="external">${comment.person}</a> said</h3>
      [#else]<h3>${comment.person} said</h3>
      [/#if]
      <p>${comment.summary}</p>
      <p><time datetime="${comment.date?string("yyyy-MM-dd'T'HH:mm:ss")}">${comment.printableDate}</time></p>
    </div>
    [/#list]
  [/#if]
  [#if enableComments?? = true]
    <section>
    [#assign addURL][@hst.actionURL][@hst.param name="type" value="add"/][/@hst.actionURL][/#assign]
    <form method="post" action="${addURL}" class="commentform" id="commentform">
      <fieldset>
        <legend>Leave your comment here</legend>
        <ul>
            <li><label for="person">Name: *</label><input type="text" id="person" name="person" required="required"/></li>
            <li><label for="email">Email:</label><input type="email" id="email" name="email"/> </li>
            <li><label for="website">Website:</label><input type="url" id="website" name="website"/> </li>
            <li><label for="comment">Comment: *</label><textarea name="comment" id="comment" rows="4" cols="40" required="required"></textarea></li>
            <li><input type="submit" value="Submit"/></li>
        </ul>
      </fieldset>
      <p>* required field</p>
    </form>
  </section>
  [/#if]
</article>

