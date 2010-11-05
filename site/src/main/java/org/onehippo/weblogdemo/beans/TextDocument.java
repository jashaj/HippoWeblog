package org.onehippo.weblogdemo.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "website2010:textdocument")
public class TextDocument extends BaseDocument {

    /*
     * (non-Javadoc)
     * @see org.onehippo.weblogdemo.beans.BaseDocument#getType()
     */
    @Override
    public String getType() {
        return "textdocument";
    }

    public String getDayOfDate() {
        return getDateString(getDate(), "d");
    }

    public String getMonthOfDate() {
        return getDateString(getDate(), "MMM");
    }

    public String getYearOfDate() {
        return getDateString(getDate(), "yyyy");
    }

    public List<String> getTags() {
        List<String> tagList = new ArrayList<String>();
        Object tags = getProperty("hippostd:tags");
        if (tags instanceof String && StringUtils.isNotBlank((String) tags)) {
            tagList.add((String) tags);
        } else if (tags instanceof String[]) {
            for (String tag : (String[]) tags) {
                if (StringUtils.isNotBlank(tag)) {
                    tagList.add(tag);
                }
            }
        }
        return tagList;
    }

}
