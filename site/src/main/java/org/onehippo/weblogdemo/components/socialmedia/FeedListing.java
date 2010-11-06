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
package org.onehippo.weblogdemo.components.socialmedia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

import org.onehippo.weblogdemo.components.BaseSiteComponent;

/**
 * HST component that fetches an RSS or Atom feed.
 * @author Jasha Joachimsthal
 *
 */
public class FeedListing extends BaseSiteComponent {

    public static final Logger log = LoggerFactory.getLogger(FeedListing.class);

    /**
     * Fetches and RSS or Atom Feed. Accepts the following parameters:
     * @param feedLocation Required URL of the Feed
     * @param showLinks if set to true, the JSP will add links to the items
     * @param boxTitle optional parameter for the title of the box in which the feed is displayed
     * @throws HstComponentException if something goes wrong (missing parameters, unable to fetch or parse feed)
     */
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        String feedLocation = getParameter("feedLocation", request);
        if (StringUtils.isBlank(feedLocation)) {
            throw new HstComponentException(
                    "Missing or empty parameter 'feedLocation', should contain the URL of the RSS/Atom Feed.");
        }

        String showLinks = getParameter("showLinks", request);
        request.setAttribute("showLinks", Boolean.parseBoolean(showLinks));
        String boxTitle = getParameter("boxTitle", request);

        FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
        FeedFetcher feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
        SyndFeed feed;
        try {
            feed = feedFetcher.retrieveFeed(new URL(feedLocation));
            if (feed == null || feed.getEntries() == null || feed.getEntries().size() == 0) {
                return;
            }
            request.setAttribute("feed", feed);
            if (StringUtils.isBlank(boxTitle)) {
                boxTitle = feed.getTitle();
            }
            request.setAttribute("boxTitle", boxTitle);
        } catch (IllegalArgumentException e) {
            throw new HstComponentException("Parameter 'feedLocation' is null, should contain the URL of the feed", e);
        } catch (MalformedURLException e) {
            throw new HstComponentException("The URL '" + feedLocation + "' is malformed", e);
        } catch (IOException e) {
            throw new HstComponentException("Connection error retrieving feed ", e);
        } catch (FeedException e) {
            throw new HstComponentException("Received invalid feed which makes it impossible to parse", e);
        } catch (FetcherException e) {
            throw new HstComponentException("HTTP error retrieving feed", e);
        }

    }
}
