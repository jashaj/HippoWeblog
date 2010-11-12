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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.weblogdemo.beans.BaseDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onehippo.forge.weblogdemo.beans.BeanConstants;
import org.onehippo.forge.weblogdemo.beans.Blogpost;

/**
 * Component for a document overview. Queries a folder and puts a paged resultset on the request.
 *
 * @author Jasha Joachimsthal
 */
public class Overview extends BaseSiteComponent {

    public static final Logger log = LoggerFactory.getLogger(Overview.class);
    public static final int PAGESIZE = 10;

    /*
     * (non-Javadoc)
     * @see org.onehippo.forge.weblogdemo.components.BaseSiteComponent#doBeforeRender(org.hippoecm.hst.core.component.HstRequest, org.hippoecm.hst.core.component.HstResponse)
     */

    @SuppressWarnings("unchecked")
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        HippoBean n = getContentBean(request);
        if (!(n instanceof HippoFolderBean)) {
            response.setStatus(HstResponse.SC_NOT_FOUND);
            return;
        }

        // TODO: make subclasses for this Overview so we don't have to inject the beanClass
        String beanClassForOverview = getParameter("beanClassForOverview", request);
        if (StringUtils.isBlank(beanClassForOverview)) {
            beanClassForOverview = Blogpost.class.getName();
        }

        Class<? extends BaseDocument> beanClass;
        try {
            beanClass = (Class<? extends BaseDocument>) Class.forName(beanClassForOverview);
        } catch (ClassNotFoundException e) {
            throw new HstComponentException(
                    String.format("Invalid configured class '%s' for overview", beanClassForOverview), e);
        }

        String pageStr = request.getParameter("page");

        int page = 0;
        if (StringUtils.isNotBlank(pageStr)) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                log.debug("Value '{}' cannot be converted to an Integer", pageStr);
            }
        }

        request.setAttribute("page", page);
        request.setAttribute("pageTitle", getParameter("pageTitle", request));

        try {
            HstQuery hstQuery = getQueryManager().createQuery(n, beanClass);
            hstQuery.addOrderByDescending(BeanConstants.PROP_DATE);
            HstQueryResult result = hstQuery.execute();
            if (result != null) {
                request.setAttribute("documents", queryResultAsList(request, page, result));
            }
        } catch (QueryException e) {
            log.warn("Error in querying blogposts", e);
        }
    }

    /**
     * Calculates the # of pages for the resultset.
     * Iterates over query result for the requested "page" and adds the {@link Blogpost} beans to the List of documents.
     *
     * @param request {@link org.hippoecm.hst.core.component.HstRequest}
     * @param page    current page number
     * @param result  {@link org.hippoecm.hst.content.beans.query.HstQueryResult}
     * @return List of {@link org.hippoecm.hst.content.beans.standard.HippoBean}, may be empty
     */
    protected List<HippoBean> queryResultAsList(HstRequest request, int page,
                                                HstQueryResult result) {
        List<HippoBean> documents = new ArrayList<HippoBean>();
        HippoBeanIterator beans = result.getHippoBeans();
        if (beans == null) {
            return documents;
        }

        long beansSize = beans.getSize();
        long pages = beans.getSize() % PAGESIZE > 0L ? beansSize / PAGESIZE + 1L : beansSize / PAGESIZE;

        request.setAttribute("pages", pages);
        if (beansSize > page * ((long) PAGESIZE)) {
            beans.skip(page * PAGESIZE);
        }

        int results = 0;
        while (beans.hasNext() && results < PAGESIZE) {
            HippoBean bean = beans.next();
            if (bean != null) { // document may have been deleted after searching
                documents.add(bean);
                results++;
            }
        }
        return documents;
    }

}
