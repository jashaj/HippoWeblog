package org.onehippo.weblogdemo.beans;

import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

/**
 * Annotated bean for {@link Node} of type {@link BeanConstants#DOCTYPE_COMMENT}
 * @author jashaj
 *
 */
@Node(jcrType = BeanConstants.DOCTYPE_COMMENT)
public class CommentBean extends TextDocument {
    private static final String COMMENT = "comment";
    private String person;
    private String email;
    private String website;
    private String summary;

    @Override
    public String getType() {
        return COMMENT;
    }

    @Override
    public String getSummary() {
        return summary == null ? (String) getProperty(BeanConstants.PROP_SUMMARY) : summary;
    }

    @Override
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPerson() {
        return person == null ? getHtmlEscapedProperty("website2010:name") : person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getEmail() {
        return email == null ? getHtmlEscapedProperty("website2010:email") : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website == null ? getHtmlEscapedProperty("website2010:website") : website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBloggerIdRef() {
        return getProperty("website2010:bloggeridref");
    }

    private String commentToUuidOfHandle;

    public void setCommentTo(String commentToUuidOfHandle) {
        this.commentToUuidOfHandle = commentToUuidOfHandle;
    }

    public BaseDocument getCommentTo() {
        HippoBean bean = getBean("website2010:commentlink");
        if (!(bean instanceof CommentLinkBean)) {
            return null;
        }
        CommentLinkBean commentLinkBean = (CommentLinkBean) bean;
        HippoBean b = commentLinkBean.getReferencedBean();
        if (!(b instanceof BaseDocument)) {
            return null;
        }
        return (BaseDocument) b;
    }

    public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
        super.bind(content, node);
        try {
            BaseDocument bean = (BaseDocument) content;
            node.setProperty(BeanConstants.PROP_DATE, bean.getCalendar());
            node.setProperty("website2010:name", getPerson());
            node.setProperty("website2010:email", getEmail());
            node.setProperty("website2010:website", getWebsite());
            node.setProperty(BeanConstants.PROP_SUMMARY, getSummary());

            javax.jcr.Node commentLink = null;
            if (node.hasNode("website2010:commentlink")) {
                commentLink = node.getNode("website2010:commentlink");
            } else {
                commentLink = node.addNode("website2010:commentlink", "website2010:commentlink");
            }
            commentLink.setProperty("hippo:docbase", commentToUuidOfHandle);
            commentLink.setProperty("hippo:values", new String[0]);
            commentLink.setProperty("hippo:modes", new String[0]);
            commentLink.setProperty("hippo:facets", new String[0]);

        } catch (Exception e) {
            throw new ContentNodeBindingException(e);
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
