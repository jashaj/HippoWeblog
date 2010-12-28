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
[#setting locale="en_GB"]
<section>
  [#if boxTitle?has_content]<h2>${boxTitle}</h2>[/#if]
  [#if statuses?has_content]
  <ul>
    [#compress]
    [#list statuses as status]
    <li>
      [#if status.retweet=true]
        RT ${status.retweetedStatus.user.screenName}: ${status.retweetedStatus.text}<br />
        Retweeted at
        <a href="http://twitter.com/${status.retweetedStatus.user.screenName}/status/${status.retweetedStatus.id}" rel="external">
          <time datetime="${status.createdAt?string("yyyy-MM-dd'T'HH:mm:ss")}">${status.createdAt?string("d MMM H:mm")}</time></a>
      [#else]${status.text}<br />
        <a href="http://twitter.com/${status.user.screenName}/status/${status.id}" rel="external">
          <time datetime="${status.createdAt?string("yyyy-MM-dd'T'HH:mm:ss")}">${status.createdAt?string("d MMM H:mm")}</time></a>
      [/#if]
    </li>
    [#if status_index=4][#break/][/#if]
    [/#list]
    [/#compress]
  </ul>
  [#else]No tweets found
  [/#if]
</section>
