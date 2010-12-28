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
<section id="flickr">
  <h2>Flickr</h2>
  [@compress single_line=true]
  <object type="application/x-shockwave-flash" data="http://media.roytanck.com/flickrwidget.swf" width="200" height="200">
    <param name="movie" value="http://media.roytanck.com/flickrwidget.swf" />
    <param name="bgcolor" value="#ffffff" /><param name="wmode" value="transparent" />
    [#-- in flashvars goes the url for your personal Flickr feed --]
    <param name="flashvars" value="feed=http%3A//api.flickr.com/services/feeds/photos_public.gne%3Fid%3D68517806@N00%26lang%3Den-us%26format%3Drss_200" />
    <param name="AllowScriptAccess" value="always"/><p><a href="http://www.roytanck.com">Roy Tanck</a>'s Flickr Widget requires Flash Player 9 or better.</p>
  </object>
  <span style="font-size:9px;">Get this widget at <a href="http://www.roytanck.com">roytanck.com</a></span>
  [/@compress]
</section>