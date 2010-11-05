package org.onehippo.weblogdemo.beans;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.utils.SimpleHtmlExtractor;

import org.onehippo.weblogdemo.components.Overview;

/**
 * Annotated bean for {@link Node} of type {@link BeanConstants#DOCTYPE_BLOGPOST}
 * @author jashaj
 *
 */
@Node(jcrType = BeanConstants.DOCTYPE_BLOGPOST)
public class Blogpost extends TextDocument {

    private static final String BLOGPOST = "blogpost";
    private static final String HTMLTAG = "\\<.*?\\>";
    private final static String PARAGRAPH = "p";

    /*
     * (non-Javadoc)
     * @see org.onehippo.weblogdemo.beans.TextDocument#getType()
     */
    @Override
    public String getType() {
        return BLOGPOST;
    }

    /**
     * Utility method to return text for the summary in an {@link Overview} page.<br />
     * If the summary field is empty, the first paragraph from the body is used.
     * @return summary for an {@link Overview} page
     */
    public String getOverviewSummary() {
        if (StringUtils.isNotBlank(getSummary())) {
            return getSummary();
        } else {
            HippoHtml hippoHtml = getHtml();
            if (hippoHtml != null && StringUtils.isNotBlank(hippoHtml.getContent())) {
                String innerHTML = SimpleHtmlExtractor.getInnerHtml(hippoHtml.getContent(), PARAGRAPH, false);
                if (innerHTML != null) {
                    StrTokenizer strTokenizer = new StrTokenizer(innerHTML, '\n');
                    StringBuffer sb = new StringBuffer();
                    for (String s : strTokenizer.getTokenArray()) {
                        sb.append(StringUtils.trimToEmpty(s)).append(' ');
                    }
                    return sb.toString().replaceAll(HTMLTAG, StringUtils.EMPTY);
                }
            }
        }
        return null;
    }

    /**
     * @return value of property {@link BeanConstants#PROP_BLOGGERID}
     */
    public String getBloggerId() {
        return getProperty(BeanConstants.PROP_BLOGGERID);
    }
}
