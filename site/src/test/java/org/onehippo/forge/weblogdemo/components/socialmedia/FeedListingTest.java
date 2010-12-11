package org.onehippo.forge.weblogdemo.components.socialmedia;

import com.sun.syndication.feed.synd.SyndFeed;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test class for {@link org.onehippo.forge.weblogdemo.components.socialmedia.FeedListing} .
 *
 * @author Jasha Joachimsthal
 */
public class FeedListingTest {
    
    @Test
    public void testReturnNullOnNonExistingFeedLocation() throws Exception {
        String feedLocation = "http://example.com/feed.rss";
        FeedListing feedListing = new FeedListing();
        SyndFeed feed = feedListing.getFeed(feedLocation);
        assertNull("feed is null", feed);
    }

    @Test
    public void testReturnFeed() throws Exception {
        String feedLocation = "http://planet.apache.org/committers/atom.xml";
        FeedListing feedListing = new FeedListing();
        SyndFeed feed = feedListing.getFeed(feedLocation);
        assertNotNull("feed is not null", feed);
    }
}
