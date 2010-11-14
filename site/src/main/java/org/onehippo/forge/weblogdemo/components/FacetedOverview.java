package org.onehippo.forge.weblogdemo.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetChildNavigationBean;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetSubNavigation;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onehippo.forge.weblogdemo.beans.Blogpost;

public class FacetedOverview extends BaseSiteComponent {
    public static final Logger log = LoggerFactory.getLogger(Overview.class);
    public static final int PAGESIZE = 10;

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        HippoBean n = getContentBean(request);
        List<Blogpost> documents = new ArrayList<Blogpost>();

        String pageStr = request.getParameter("page");
        int page = 0;
        if (StringUtils.isNotBlank(pageStr)) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // empty ignore
            }
        }
        request.setAttribute("page", page);
        request.setAttribute("pageTitle", getParameter("pageTitle", request));

        if (n instanceof HippoFacetChildNavigationBean) {
            HippoFacetChildNavigationBean facetNav = (HippoFacetChildNavigationBean) n;
            HippoDocumentIterator<Blogpost> it = facetNav.getResultSet().getDocumentIterator(Blogpost.class);
            long pages = facetNav.getResultSet().getCount() / PAGESIZE;
            pages = facetNav.getResultSet().getCount() % PAGESIZE > 0L ? pages + 1L : pages;

            if (log.isDebugEnabled()) {
                log.debug("Resultset of {}, pages {}", facetNav.getResultSet().getCount(), pages);
            }
            request.setAttribute("pages", pages);

            int skip = page * PAGESIZE;
            it.skip(skip);
            while (it.hasNext() && it.getPosition() < 10 + (skip - 1)) {
                // the it.getPosition gets increased on it.next() call, hence above, skip - 1
                documents.add(it.next());
            }

        }

        if (n instanceof HippoFacetSubNavigation) {
            request.setAttribute("subnavigation", n);
        }
        request.setAttribute("documents", documents);
    }
}
