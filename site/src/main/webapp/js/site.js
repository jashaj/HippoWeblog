/**
 * Copyright 2010 Jasha Joachimsthal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
function init(){externalizeLinks();toggleFacets();$("a.grouped_elements").fancybox();SyntaxHighlighter.all()}function externalizeLinks(){$('a[rel="external"]').addClass("external");$('a[rel="external"]').hover(function(){$(this).attr("title","Opens in new window: "+$(this).text())});$('a[rel="external"]').click(function(){window.open($(this).attr("href"));return false})}function toggleFacets(){$("a.toggle").click(function(){$(this).parent().parent().find(".more").toggle();$(this).parent().parent().find(".less").toggle()})}$("#commentform").submit(function(){var valid=true;if($("#person").val()===""){$("#person").parent().addClass("error");valid=false}else{$("#person").parent().removeClass("error")}if($("#comment").val()===""){$("#comment").parent().addClass("error");valid=false}else{$("#comment").parent().removeClass("error")}return valid});