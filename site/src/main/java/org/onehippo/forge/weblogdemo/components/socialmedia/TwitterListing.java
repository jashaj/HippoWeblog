/*
 * Copyright 2010-2011 Jasha Joachimsthal
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

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.weblogdemo.components.BaseSiteComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Component for displaying Tweets using Twitter4J
 *
 * @author Jasha Joachimsthal
 */
public class TwitterListing extends BaseSiteComponent {

    private static final String CONSUMER_SECRET = "consumerSecret";
    private static final String CONSUMER_KEY = "consumerKey";
    private static final String TOKEN_SECRET = "tokenSecret";
    private static final String TOKEN = "token";
    public static final Logger log = LoggerFactory.getLogger(TwitterListing.class);

    /**
     * Fetches both Tweets and ReTweets for the configured user.
     * Adds them combined in a reversed order (newest first) to the {@link HstRequest}.
     * <p/>
     * Required parameters:
     * <dl>
     * <dt>token</dt>
     * <dd>needed for oAuth {@link twitter4j.http.AccessToken}</dd>
     * <dt>tokenSecret</dt>
     * <dd>needed for oAuth {@link twitter4j.http.AccessToken}</dd>
     * <dt>consumerKey<dt>
     * <dd>needed for oAuth</dd>
     * <dt>consumerSecret<dt>
     * <dd>needed for oAuth</dd>
     * </dl>
     */
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        request.setAttribute("boxTitle", getParameter("boxTitle", request));

        Twitter twitter = getTwitterObject(request);
        if (twitter == null) {
            return;
        }

        try {
            List<Status> statuses = twitter.getUserTimeline();
            // for backwards compatibility Twitter does not return retweets when the user timeline is requested
            // that's why we have to merge them
            List<Status> retweets = twitter.getRetweetedByMe();
            statuses.addAll(retweets);
            Set<Status> allStatuses = new TreeSet<Status>(Collections.reverseOrder());
            allStatuses.addAll(statuses);
            allStatuses.addAll(retweets);
            if (!allStatuses.isEmpty()) {
                request.setAttribute("statuses", allStatuses);
            }
        } catch (TwitterException e) {
            log.warn("Error getting Twitter status updates", e);
        }

    }

    /**
     * Builds up the {@link Twitter} object needed to communicate trhrough Twitter4J
     *
     * @param request current {@link org.hippoecm.hst.core.component.HstRequest}
     * @return {@link Twitter} or {@literal null} if configuration is missing in the HST
     */
    private Twitter getTwitterObject(HstRequest request) {
        String token = getParameter(TOKEN, request);
        String tokenSecret = getParameter(TOKEN_SECRET, request);
        String consumerKey = getParameter(CONSUMER_KEY, request);
        String consumerSecret = getParameter(CONSUMER_SECRET, request);

        boolean missingConfiguration = false;
        if (StringUtils.isBlank(token)) {
            missingConfiguration = true;
            log.info("Missing or empty parameter 'token'");
        } else if (StringUtils.isBlank(tokenSecret)) {
            missingConfiguration = true;
            log.info("Missing or empty parameter 'tokenSecret'");
        } else if (StringUtils.isBlank(consumerKey)) {
            missingConfiguration = true;
            log.info("Missing or empty parameter 'consumerKey'");
        } else if (StringUtils.isBlank(consumerSecret)) {
            missingConfiguration = true;
            log.info("Missing or empty parameter 'consumerSecret'");
        }

        if (missingConfiguration) {
            return null;
        }

        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret);
        
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        return twitterFactory.getInstance();
    }

}
