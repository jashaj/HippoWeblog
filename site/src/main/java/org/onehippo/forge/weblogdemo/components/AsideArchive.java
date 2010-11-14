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

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetChildNavigationBean;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetNavigation;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetsAvailableNavigation;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Renders the Archive for blogposts
 * @author Jasha Joachimsthal
 *
 */
public class AsideArchive extends BaseSiteComponent {

    /**
     * Gets the current Faceted bean, puts it on the request
     * Location of the faceted bean can be configured with parameter facetLocation
     */
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        HippoBean currentBean;
        if (StringUtils.isNotBlank(getParameter("facetLocation", request))) {
            currentBean = this.getSiteContentBaseBean(request).getBean(getParameter("facetLocation", request));
        } else {
            currentBean = this.getContentBean(request);
        }
        if (currentBean instanceof HippoFacetNavigation || currentBean instanceof HippoFacetChildNavigationBean) {
            request.setAttribute("facetnav", currentBean);
            if (currentBean instanceof HippoFacetsAvailableNavigation) {
                request.setAttribute("availNav", "true");
            }
            if (currentBean instanceof HippoFacetChildNavigationBean) {
                request.setAttribute("childNav", "true");
            }
        }

    }
}
