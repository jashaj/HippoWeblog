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

import org.hippoecm.hst.content.beans.Node;

/**
 * Annotated bean for {@link Node} of type {@link BeanConstants#DOCTYPE_BLOGPOST}
 * @author Jasha Joachimsthal
 *
 */
@Node(jcrType = BeanConstants.DOCTYPE_BLOGPOST)
public class Blogpost extends TextDocument {

    private static final String BLOGPOST = "blogpost";

    /*
     * (non-Javadoc)
     * @see org.onehippo.forge.weblogdemo.beans.TextDocument#getType()
     */
    @Override
    public String getType() {
        return BLOGPOST;
    }


    /**
     * @return value of property {@link BeanConstants#PROP_BLOGGERID}
     */
    public String getBloggerId() {
        return getProperty(BeanConstants.PROP_BLOGGERID);
    }
}
