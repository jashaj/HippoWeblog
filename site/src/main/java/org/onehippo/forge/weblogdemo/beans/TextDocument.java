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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.utils.SimpleHtmlExtractor;

@Node(jcrType = "weblogdemo:textdocument")
public class TextDocument extends BaseDocument {
    private static final String HTMLTAG = "\\<.*?\\>";
    private final static String PARAGRAPH = "p";

    /*
     * (non-Javadoc)
     * @see org.onehippo.forge.weblogdemo.beans.BaseDocument#getType()
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

        /**
     * Utility method to return text for the summary in an {@link org.onehippo.forge.weblogdemo.components.Overview} page.<br />
     * If the summary field is empty, the first paragraph from the body is used.
     * @return summary for an {@link org.onehippo.forge.weblogdemo.components.Overview} page
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
}
