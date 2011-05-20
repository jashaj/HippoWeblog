/*
 * Copyright 2008-2010 Hippo
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
package org.onehippo.forge.weblogdemo.hstextensions;

import javax.jcr.Node;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.rewriter.impl.SimpleContentRewriter;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.utils.SimpleHtmlExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ContentRewriterImpl does the same as {@link SimpleContentRewriter}. 
 * Supports adding rel="external" to external links or rewriting all links to external form, e.g. http://example.com/link.html.
 * @author jashaj
 *
 */
public class ContentRewriterImpl extends SimpleContentRewriter {
    private static final Logger log = LoggerFactory.getLogger(ContentRewriterImpl.class);
    private static final String REL_EXTERNAL = " rel=\"external\"";

    /**
     * @see SimpleContentRewriter#rewrite(String, Node, HstRequestContext)
     */
    @Override
    public String rewrite(String html, Node node, HstRequestContext requestContext) {
        return rewriteContent(html, node, requestContext, false);
    }

    /**
     * Rewrites all links to external form, e.g. http://example.com/link.html. Useful for content exchange through feeds
     * @param html String of the HTML
     * @param node {@link Node} that contains the HTML
     * @param requestContext {@link HstRequestContext}
     * @return rewritten String of the HTML with all links rewritten to external form
     */
    public String rewriteToExternal(String html, Node node, HstRequestContext requestContext) {
        return rewriteContent(html, node, requestContext, true);
    }

    /**
     * Internal method that rewrites all links, optionally makes links in external form or adds a rel="external" to external links
     * @param html String of the HTML
     * @param node {@link Node} that contains the HTML
     * @param requestContext {@link HstRequestContext}
     * @param externalizeLinks boolean that defines if all links should be rewritten to external form, e.g. http://example.com/link.html
     * @return rewritten String of the HTML
     */
    private String rewriteContent(String html, Node node, HstRequestContext requestContext,
            boolean externalizeLinks) {
        // only create if really needed
        StringBuilder sb = null;

        // strip off html & body tag
        String innerHTML = SimpleHtmlExtractor.getInnerHtml(html, "body", false);
        if (innerHTML == null) {
            innerHTML = html;
        }
        innerHTML = innerHTML.trim().replaceAll("facetselect=\".+?\"", "");

        int globalOffset = 0;
        while (innerHTML.indexOf(LINK_TAG, globalOffset) > -1) {
            int offset = innerHTML.indexOf(LINK_TAG, globalOffset);

            int hrefIndexStart = innerHTML.indexOf(HREF_ATTR_NAME, offset);
            if (hrefIndexStart == -1) {
                break;
            }

            if (sb == null) {
                sb = new StringBuilder(html.length());
            }

            hrefIndexStart += HREF_ATTR_NAME.length();
            offset = hrefIndexStart;
            int endTag = innerHTML.indexOf(END_TAG, offset);
            boolean appended = false;
            if (hrefIndexStart < endTag) {
                int hrefIndexEnd = innerHTML.indexOf(ATTR_END, hrefIndexStart);
                if (hrefIndexEnd > hrefIndexStart) {
                    String documentPath = innerHTML.substring(hrefIndexStart, hrefIndexEnd);

                    offset = endTag;
                    sb.append(innerHTML.substring(globalOffset, hrefIndexStart));

                    if (isExternal(documentPath)) {
                        sb.append(documentPath);
                    } else {
                        HstLink href = getDocumentLink(documentPath, node, requestContext, (Mount) null);
                        if (href != null && href.getPath() != null) {
                            sb.append(href.toUrlForm(requestContext, externalizeLinks));
                        } else {
                            log.warn("Skip href because url is null");
                        }
                    }
                    sb.append(innerHTML.substring(hrefIndexEnd, endTag));
                    if (!externalizeLinks && isExternal(documentPath)) {
                        sb.append(REL_EXTERNAL);
                    }
                    appended = true;
                }
            }
            if (!appended && offset > globalOffset) {
                sb.append(innerHTML.substring(globalOffset, offset));
            }
            globalOffset = offset;
        }

        if (sb != null) {
            sb.append(innerHTML.substring(globalOffset, innerHTML.length()));
            innerHTML = String.valueOf(sb);
            sb = null;
        }

        globalOffset = 0;
        while (innerHTML.indexOf(IMG_TAG, globalOffset) > -1) {
            int offset = innerHTML.indexOf(IMG_TAG, globalOffset);

            int srcIndexStart = innerHTML.indexOf(SRC_ATTR_NAME, offset);

            if (srcIndexStart == -1) {
                break;
            }

            if (sb == null) {
                sb = new StringBuilder(innerHTML.length());
            }
            srcIndexStart += SRC_ATTR_NAME.length();
            offset = srcIndexStart;
            int endTag = innerHTML.indexOf(END_TAG, offset);
            boolean appended = false;
            if (srcIndexStart < endTag) {
                int srcIndexEnd = innerHTML.indexOf(ATTR_END, srcIndexStart);
                if (srcIndexEnd > srcIndexStart) {
                    String srcPath = innerHTML.substring(srcIndexStart, srcIndexEnd);

                    offset = endTag;
                    sb.append(innerHTML.substring(globalOffset, srcIndexStart));

                    if (isExternal(srcPath)) {
                        sb.append(srcPath);
                    } else {
                        HstLink binaryLink = getBinaryLink(srcPath, node, requestContext, (Mount) null);
                        if (binaryLink != null && binaryLink.getPath() != null) {
                            sb.append(binaryLink.toUrlForm(requestContext, externalizeLinks));
                        } else {
                            log.warn("Could not translate image src. Skip src");
                        }
                    }

                    sb.append(innerHTML.substring(srcIndexEnd, endTag));
                    appended = true;
                }
            }
            if (!appended && offset > globalOffset) {
                sb.append(innerHTML.substring(globalOffset, offset));
            }
            globalOffset = offset;
        }

        if (sb == null) {
            return innerHTML;
        } else {
            sb.append(innerHTML.substring(globalOffset, innerHTML.length()));
            return sb.toString();
        }
    }
}
