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
package org.onehippo.weblogdemo.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "weblogdemo:textdocument")
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
