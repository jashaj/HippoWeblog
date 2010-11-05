package org.onehippo.weblogdemo.components.socialmedia;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import org.onehippo.weblogdemo.components.BaseSiteComponent;

/**
 * Component for displaying Tweets using Twitter4J
 * @author jashaj
 *
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
     * 
     * @param token needed for oAuth {@link AccessToken}
     * @param tokenSecret needed for oAuth {@link AccessToken}
     * @param consumerKey needed for oAuth
     * @param consumerSecret needed for oAuth
     * @throws HstComponentException if one of the parameters are missing or blank
     */
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        String token = getParameter(TOKEN, request);
        String tokenSecret = getParameter(TOKEN_SECRET, request);
        String consumerKey = getParameter(CONSUMER_KEY, request);
        String consumerSecret = getParameter(CONSUMER_SECRET, request);

        if (StringUtils.isBlank(token)) {
            throw new HstComponentException("Missing or empty parameter 'token'");
        } else if (StringUtils.isBlank(tokenSecret)) {
            throw new HstComponentException("Missing or empty parameter 'tokenSecret'");
        } else if (StringUtils.isBlank(consumerKey)) {
            throw new HstComponentException("Missing or empty parameter 'consumerKey'");
        } else if (StringUtils.isBlank(consumerSecret)) {
            throw new HstComponentException("Missing or empty parameter 'consumerSecret'");
        }

        AccessToken accessToken = new AccessToken(token, tokenSecret);
        Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance(consumerKey, consumerSecret, accessToken);

        try {
            List<Status> statuses = twitter.getUserTimeline();
            List<Status> retweets = twitter.getRetweetedByMe();
            statuses.addAll(retweets);
            Set<Status> allStatuses = new TreeSet<Status>(Collections.reverseOrder());
            allStatuses.addAll(statuses);
            allStatuses.addAll(retweets);
            if (!allStatuses.isEmpty()) {
                request.setAttribute("statuses", allStatuses);
                request.setAttribute("boxTitle", getParameter("boxTitle", request));
            }
        } catch (TwitterException e) {
            log.warn("Error getting Twitter status updates", e);
        }

    }
}
