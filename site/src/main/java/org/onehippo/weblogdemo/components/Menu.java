package org.onehippo.weblogdemo.components;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class Menu extends BaseSiteComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        request.setAttribute("menu", request.getRequestContext().getHstSiteMenus().getSiteMenu("main"));

        String query = getPublicRequestParameter(request, "searchfor");
        if (StringUtils.isBlank(query)) {
            query = request.getParameter("searchfor");
        }
        if (StringUtils.isNotBlank(query)) {
            request.setAttribute("searchfor", StringEscapeUtils.escapeHtml(query));
        }
    }

}
