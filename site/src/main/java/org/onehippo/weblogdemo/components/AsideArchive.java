package org.onehippo.weblogdemo.components;

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
 * @author jashaj
 *
 */
public class AsideArchive extends BaseSiteComponent {

    /**
     * Gets the current Faceted bean, puts it on the request
     * Location of the faceted bean can be configured with parameter facetLocation
     */
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {

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

        setComponentLabels(request);

    }
}
