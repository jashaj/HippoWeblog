package org.onehippo.forge.weblogdemo.components;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.weblogdemo.beans.Blogpost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * .HST component that queries the repository for the newest {@link org.onehippo.forge.weblogdemo.beans.Blogpost} based on the date
 *  Needs HST parameter {@literal blogFolder} that contains the path to the blogposts relative from the site root.
 *
 * @author Jasha Joachimsthal
 */
public class LatestBlog extends BaseSiteComponent {

    private static final Logger log = LoggerFactory.getLogger(LatestBlog.class);

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        HippoBean latestBlog = getLatestBlog(request);

        if (latestBlog == null) {
            log.debug("No blogposts");
            return;
        }
        request.setAttribute("document", latestBlog);

    }

    private HippoBean getLatestBlog(HstRequest request) {
        String blogFolder = getParameter("blogFolder", request);
        if (StringUtils.isBlank(blogFolder)) {
            log.warn("No path configured for parameter 'blogFolder' in HST config. Will not execute query");
            return null;
        }

        HippoBean blogsFolderBean = getSiteContentBaseBean(request).getBean(blogFolder, HippoFolderBean.class);

        try {
            HstQuery hstQuery = getQueryManager().createQuery(blogsFolderBean, Blogpost.class);
            hstQuery.addOrderByDescending("weblogdemo:date");
            hstQuery.setLimit(1);

            HstQueryResult result = hstQuery.execute();

            if (result == null || result.getSize() == 0) {
                return null;
            }

            HippoBeanIterator beanIterator = result.getHippoBeans();
            while (beanIterator.hasNext()) {
                HippoBean bean = beanIterator.next();
                if (bean != null) {
                    return bean;
                }
            }
        } catch (QueryException e) {
            log.warn("Could not execute query to fetch latest blogpost", e);
        }
        return null;
    }
}
