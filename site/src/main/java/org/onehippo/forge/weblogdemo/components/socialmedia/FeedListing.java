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
package org.onehippo.forge.weblogdemo.components.socialmedia;

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

import org.onehippo.forge.weblogdemo.components.BaseSiteComponent;

/**
 * HST component that fetches an RSS or Atom feed.
 *
 * @author Jasha Joachimsthal
 */
public class FeedListing extends BaseSiteComponent {

    public static final Logger log = LoggerFactory.getLogger(FeedListing.class);

    /**
     * Fetches and RSS or Atom Feed. Accepts the following parameters:
     * <p/>
     * Accepts the following HST configuration parameters
     * <dl>
     * <dt>feedLocation</dt>
     * <dd>URL of the Feed. Required parameter.</dd>
     * <dt>showLinks</dt>
     * <dd>boolean to indicate whether the rendering templates should add links to the feeditems. Default is {@literal false}.</dd>
     * <dt>boxTitle</dt>
     * <dd>optional title for the feed listing in the rendering template. Default is the title returned by the RSS or Atom feed.</dd>
     * </dl>
     *
     * @throws HstComponentException if the configured feedLocation is missing
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
        
        SyndFeed feed = getFeed(feedLocation);
        if (feed != null) {
            request.setAttribute("feed", feed);
            if (StringUtils.isBlank(boxTitle)) {
                boxTitle = feed.getTitle();
            }
        }
        request.setAttribute("boxTitle", boxTitle);
    }

    /**
     * Gets a (cached) {@link com.sun.syndication.feed.synd.SyndFeed} for the given location
     *
     * @param feedLocation String of the URL of the RSS/Atom feed
     * @return {@link com.sun.syndication.feed.synd.SyndFeed} for the location or {@literal null} in case of connection errors
     */
    SyndFeed getFeed(String feedLocation) {
        SyndFeed feed;
        try {
            FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
            FeedFetcher feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
            return feedFetcher.retrieveFeed(new URL(feedLocation));
        } catch (IOException e) {
            log.error("Connection error retrieving feed ", e);
            return null;
        } catch (FeedException e) {
            log.error("Received invalid feed which makes it impossible to parse", e);
            return null;
        } catch (FetcherException e) {
            log.error("HTTP error retrieving feed", e);
            return null;
        }
    }
}
