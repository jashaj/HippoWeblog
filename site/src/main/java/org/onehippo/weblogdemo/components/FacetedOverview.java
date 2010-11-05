package org.onehippo.weblogdemo.components;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetChildNavigationBean;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetSubNavigation;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onehippo.weblogdemo.beans.Blogpost;

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
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            // empty ignore
        }
        request.setAttribute("page", page);

        if (n instanceof HippoFacetChildNavigationBean) {
            HippoFacetChildNavigationBean facetNav = (HippoFacetChildNavigationBean) n;
            HippoDocumentIterator<Blogpost> it = facetNav.getResultSet().getDocumentIterator(Blogpost.class);
            long pages = facetNav.getResultSet().getCount() / PAGESIZE;
            if (facetNav.getResultSet().getCount() % PAGESIZE > 0L) {
                pages = pages + 1L;
            }
            if (log.isDebugEnabled()) {
                log.debug("Resultset of " + facetNav.getResultSet().getCount() + " pages " + pages);
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
