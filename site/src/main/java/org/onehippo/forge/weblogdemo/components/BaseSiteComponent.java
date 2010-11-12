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
package org.onehippo.forge.weblogdemo.components;

import java.util.Map;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.container.ComponentManager;
import org.onehippo.forge.properties.api.PropertiesManager;

/**
 * Base component for this project.
 *
 * @author Jasha Joachimsthal
 */
public class BaseSiteComponent extends BaseHstComponent {

    /*
     * (non-Javadoc)
     * @see org.hippoecm.hst.core.component.GenericHstComponent#doBeforeRender(org.hippoecm.hst.core.component.HstRequest, org.hippoecm.hst.core.component.HstResponse)
     */

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        request.setAttribute("cssClass", getParameter("cssClass", request));
    }

    /**
     * Utility method to retrieve the configured label documents for a component
     *
     * @param request {@link HstRequest}
     */
    protected void setComponentLabels(HstRequest request) {
        // get the manager  
        ComponentManager componentManager = this.getDefaultClientComponentManager();
        PropertiesManager propertiesManager = componentManager.getComponent(PropertiesManager.class.getName());

        // retrieve labels documents
        Map<String, String> labels;
        String labelsName = this.getParameter("labelsName", request);
        if (labelsName != null) {
            labels = propertiesManager.getProperties(new String[]{labelsName}, this.getContentBean(request), this
                    .getSiteContentBaseBean(request));
        } else {
            labels = propertiesManager
                    .getProperties(this.getContentBean(request), this.getSiteContentBaseBean(request));
        }

        request.setAttribute("labels", labels);
    }
}
