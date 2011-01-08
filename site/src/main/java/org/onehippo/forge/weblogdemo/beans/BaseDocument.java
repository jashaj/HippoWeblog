/*
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
package org.onehippo.forge.weblogdemo.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringEscapeUtils;
import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

/**
 * <p>Annotated bean for {@link Node} of type {@link BeanConstants#DOCTYPE_BASEDOCUMENT}.</p>
 * <p>Acts as base bean for the current project</p>
 * @author Jasha Joachimsthal
 *
 */
@Node(jcrType = BeanConstants.DOCTYPE_BASEDOCUMENT)
public class BaseDocument extends HippoDocument implements ContentNodeBinder {

    private static final String BASEDOCUMENT = "basedocument";
    protected final static String HTML_NODEPATH = BeanConstants.COMPOUND_BODY;
    protected final static String PRINTDATE = "d MMMM yyyy HH:mm";

    private String title;
    private String summary;
    private String html;
    private Calendar calendar;

    /**
     * Utility method for the JSP to define the beantype
     * @return String of the bean type
     */
    public String getType() {
        return BASEDOCUMENT;
    }

    /**
     * @return html escaped value of property {@link BeanConstants#PROP_TITLE}
     */
    public String getTitle() {
        return title == null ? getHtmlEscapedProperty(BeanConstants.PROP_TITLE) : title;
    }

    /**
     * @return value of property {@link BeanConstants#PROP_TITLE}
     */
    public String getRawTitle() {
        return getProperty(BeanConstants.PROP_TITLE);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary == null ? getHtmlEscapedProperty(BeanConstants.PROP_SUMMARY) : summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return Calendar object for {@link BeanConstants#PROP_DATE}
     */
    public Calendar getCalendar() {
        return calendar == null ? (Calendar) getProperty(BeanConstants.PROP_DATE) : calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * 
     * @return Date object for {@link #getCalendar()} or <code>null</code> 
     */
    public Date getDate() {
        Calendar cal = getCalendar();
        if (cal == null) {
            return null;
        } else {
            return cal.getTime();
        }
    }

    /**
     * Utility method that creates a String representation of the beans's Date
     * @param date {@link Date}
     * @param formatString format String for {@link DateFormat}
     * @return String representation of the Date or <code>null</code> if <code>date</code> is <code>null</code>
     */
    public String getDateString(Date date, String formatString) {
        if (date == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(formatString, Locale.ENGLISH);
        return format.format(date);

    }

    /**
     * Method for EL call to {@link #getDateString(Date, String)}
     * @return String that can be directly used in JSP for the date
     */
    public String getPrintableDate() {
        return getDateString(getDate(), PRINTDATE);
    }

    /**
     * utility method that returns HTML Escaped value of the property
     * @param name name of the property
     * @return HTML Escaped value of the property
     */
    protected String getHtmlEscapedProperty(String name) {
        return StringEscapeUtils.escapeHtml((String) getProperty(name));
    }

    public HippoHtml getHtml() {
        return getHippoHtml(HTML_NODEPATH);
    }

    public void setHtml(String html) throws ContentNodeBindingException {
        this.html = html;
    }

    /**
     * Adds {@link javax.jcr.Node} for HTML content
     * @param body String representation of HTML content
     * @throws RepositoryException if the HTML content can't be added
     */
    public void addHtml(String body) throws RepositoryException {
        javax.jcr.Node n = this.getNode().addNode(HTML_NODEPATH, "hippostd:html");
        n.setProperty("hippostd:content", body);
    }

    public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
        try {
            BaseDocument bean = (BaseDocument) content;
            node.setProperty(BeanConstants.PROP_TITLE, bean.getTitle());
            node.setProperty(BeanConstants.PROP_SUMMARY, bean.getSummary());

            if (this.html != null) {
                if (node.hasNode(HTML_NODEPATH)) {
                    javax.jcr.Node htmlNode = node.getNode(HTML_NODEPATH);
                    if (!htmlNode.isNodeType("hippostd:html")) {
                        throw new ContentNodeBindingException("Expected html node of type 'hippostd:html' but was '"
                                + htmlNode.getPrimaryNodeType().getName() + "'");
                    }
                    htmlNode.setProperty("hippostd:content", html);
                } else {
                    javax.jcr.Node html = node.addNode(HTML_NODEPATH, "hippostd:html");
                    html.setProperty("hippostd:content", html);
                }
            }
        } catch (Exception e) {
            throw new ContentNodeBindingException(e);
        }

        return true;
    }

}
