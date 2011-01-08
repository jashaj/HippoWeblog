package org.onehippo.forge.weblogdemo.components.overview;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetChildNavigationBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.onehippo.forge.weblogdemo.beans.Blogpost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Component for an overview of documents within a facet
 *
 * @author Jasha Joachimsthal
 */
public class FacetedOverview extends AbstractListing {
    public static final Logger log = LoggerFactory.getLogger(BlogListing.class);
    public static final int PAGESIZE = 10;

    /**
     * Gets the documents within a facet for the current overview
     * {@inheritDoc}
     */
    @Override
    protected List<HippoBean> getDocumentsForOverview(HstRequest request, int page) {
        List<HippoBean> documents = new ArrayList<HippoBean>();
        HippoBean n = getContentBean(request);
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

        return documents;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
