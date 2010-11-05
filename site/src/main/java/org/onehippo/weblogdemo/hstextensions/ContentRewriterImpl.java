package org.onehippo.weblogdemo.hstextensions;

import javax.jcr.Node;

import org.hippoecm.hst.content.rewriter.impl.SimpleContentRewriter;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
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
    private final static Logger log = LoggerFactory.getLogger(ContentRewriterImpl.class);
    private final static String REL_EXTERNAL = " rel=\"external\"";

    /**
     * @see SimpleContentRewriter#rewrite(String, Node, HstRequest, HstResponse)
     */
    @Override
    public String rewrite(String html, Node node, HstRequest request, HstResponse response) {
        return rewriteContent(html, node, request, response, false);
    }

    /**
     * Rewrites all links to external form, e.g. http://example.com/link.html. Useful for content exchange through feeds
     * @param html String of the HTML
     * @param node {@link Node} that contains the HTML
     * @param request {@link HstRequest}
     * @param response {@link HstResponse}
     * @return rewritten String of the HTML with all links rewritten to external form
     */
    public String rewriteToExternal(String html, Node node, HstRequest request, HstResponse response) {
        return rewriteContent(html, node, request, response, true);
    }

    /**
     * Internal method that rewrites all links, optionally makes links in external form or adds a rel="external" to external links
     * @param html String of the HTML
     * @param node {@link Node} that contains the HTML
     * @param request {@link HstRequest}
     * @param response {@link HstResponse}
     * @param externalizeLinks boolean that defines if all links should be rewritten to external form, e.g. http://example.com/link.html
     * @return rewritten String of the HTML
     */
    private String rewriteContent(String html, Node node, HstRequest request, HstResponse response,
            boolean externalizeLinks) {
        // only create if really needed
        StringBuilder sb = null;

        // strip off html & body tag
        String innerHTML = SimpleHtmlExtractor.getInnerHtml(html, "body", false);
        if (innerHTML != null) {
            html = innerHTML;
        }
        html = html.trim().replaceAll("facetselect=\".+?\"", "");

        int globalOffset = 0;
        while (html.indexOf(LINK_TAG, globalOffset) > -1) {
            int offset = html.indexOf(LINK_TAG, globalOffset);

            int hrefIndexStart = html.indexOf(HREF_ATTR_NAME, offset);
            if (hrefIndexStart == -1) {
                break;
            }

            if (sb == null) {
                sb = new StringBuilder(html.length());
            }

            hrefIndexStart += HREF_ATTR_NAME.length();
            offset = hrefIndexStart;
            int endTag = html.indexOf(END_TAG, offset);
            boolean appended = false;
            if (hrefIndexStart < endTag) {
                int hrefIndexEnd = html.indexOf(ATTR_END, hrefIndexStart);
                if (hrefIndexEnd > hrefIndexStart) {
                    String documentPath = html.substring(hrefIndexStart, hrefIndexEnd);

                    offset = endTag;
                    sb.append(html.substring(globalOffset, hrefIndexStart));

                    if (isExternal(documentPath)) {
                        sb.append(documentPath);
                    } else {
                        HstLink href = getDocumentLink(documentPath, node, request, response);
                        if (href != null && href.getPath() != null) {
                            sb.append(href.toUrlForm(request, response, externalizeLinks));
                        } else {
                            log.warn("Skip href because url is null");
                        }
                    }
                    sb.append(html.substring(hrefIndexEnd, endTag));
                    if (!externalizeLinks && isExternal(documentPath)) {
                        sb.append(REL_EXTERNAL);
                    }
                    appended = true;
                }
            }
            if (!appended && offset > globalOffset) {
                sb.append(html.substring(globalOffset, offset));
            }
            globalOffset = offset;
        }

        if (sb != null) {
            sb.append(html.substring(globalOffset, html.length()));
            html = String.valueOf(sb);
            sb = null;
        }

        globalOffset = 0;
        while (html.indexOf(IMG_TAG, globalOffset) > -1) {
            int offset = html.indexOf(IMG_TAG, globalOffset);

            int srcIndexStart = html.indexOf(SRC_ATTR_NAME, offset);

            if (srcIndexStart == -1) {
                break;
            }

            if (sb == null) {
                sb = new StringBuilder(html.length());
            }
            srcIndexStart += SRC_ATTR_NAME.length();
            offset = srcIndexStart;
            int endTag = html.indexOf(END_TAG, offset);
            boolean appended = false;
            if (srcIndexStart < endTag) {
                int srcIndexEnd = html.indexOf(ATTR_END, srcIndexStart);
                if (srcIndexEnd > srcIndexStart) {
                    String srcPath = html.substring(srcIndexStart, srcIndexEnd);

                    offset = endTag;
                    sb.append(html.substring(globalOffset, srcIndexStart));

                    if (isExternal(srcPath)) {
                        sb.append(srcPath);
                    } else {
                        HstLink binaryLink = getBinaryLink(srcPath, node, request, response);
                        if (binaryLink != null && binaryLink.getPath() != null) {
                            sb.append(binaryLink.toUrlForm(request, response, externalizeLinks));
                        } else {
                            log.warn("Could not translate image src. Skip src");
                        }
                    }

                    sb.append(html.substring(srcIndexEnd, endTag));
                    appended = true;
                }
            }
            if (!appended && offset > globalOffset) {
                sb.append(html.substring(globalOffset, offset));
            }
            globalOffset = offset;
        }

        if (sb == null) {
            return html;
        } else {
            sb.append(html.substring(globalOffset, html.length()));
            return sb.toString();
        }
    }
}
