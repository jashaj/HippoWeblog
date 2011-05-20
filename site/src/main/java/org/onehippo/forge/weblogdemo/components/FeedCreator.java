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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetChildNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.onehippo.forge.weblogdemo.beans.BeanConstants;
import org.onehippo.forge.weblogdemo.beans.Blogpost;
import org.onehippo.forge.weblogdemo.components.overview.BlogListing;
import org.onehippo.forge.weblogdemo.hstextensions.ContentRewriterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates Atom or RSS feed using ROME. Needs a JSP that only sets the mime-type
 * Example used from http://wiki.java.net/bin/view/Javawsxml/Rome04TutorialFeedWriter
 *
 * @author Jasha Joachimsthal
 */
public class FeedCreator extends BaseSiteComponent {
  private static final String PARAM_PREFERRED_PATH = "preferredPath";
  private static final String PARAM_DESCRIPTION = "description";
  private static final String PARAM_COPYRIGHT = "copyright";
  private static final String PARAM_AUTHOR = "author";
  private static final String PARAM_TITLE = "title";
  private static final String DEFAULT_FEED_DESCRIPTION = "Feed description";
  private static final String DEFAULT_FEED_TITLE = "Feed title";
  private static final String FEEDTYPE_PARAM = "alt";
  private static final String RSS_VALUE = "rss";
  private static final String RSS_RENDERPATH = "/WEB-INF/jsp/feeds/rss.jsp";

  private static final String DEFAULT_FEED_TYPE = "atom_1.0";
  private static final String RSS_FEED_TYPE = "rss_2.0";
  private static final String APPLICATION_XHTML_XML = "application/xhtml+xml";
  private static final String XHTML_TAG_DIV_START = "<div xmlns=\"http://www.w3.org/1999/xhtml\">";
  private static final String HTML_TAG_DIV_END = "</div>";
  private static final String HTML_TAG_P_START = "<p>";
  private static final String HTML_TAG_P_END = "</p>";
  public static final Logger log = LoggerFactory.getLogger(FeedCreator.class);

  @Override
  public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
    super.doBeforeRender(request, response);
    HippoBean hippoBean = getContentBean(request);

    final String authorName = getParameter(PARAM_AUTHOR, request);

    SyndFeed feed = new SyndFeedImpl();
    setFeedMetadata(request, response, hippoBean, feed, authorName);
    setFeedEntries(request, response, hippoBean, feed, authorName);

    SyndFeedOutput output = new SyndFeedOutput();

    try {
      output.output(feed, response.getWriter(), true);
    } catch (IOException e) {
      throw new HstComponentException("Error writing feed to Response", e);
    } catch (FeedException e) {
      throw new HstComponentException("Error writing feed", e);
    }

    if (RSS_FEED_TYPE.equals(feed.getFeedType())) {
      response.setRenderPath(RSS_RENDERPATH);
    }
  }

  /**
   * Sets the List of {@link SyndEntry} to the feed
   *
   * @param request    current {@link HstRequest}
   * @param response   {@link HstResponse}
   * @param hippoBean  {@link HippoBean} for the current request
   * @param feed       {@link SyndFeed}
   * @param authorName {@link String} with the name of the author
   */
  @SuppressWarnings("unchecked")
  protected void setFeedEntries(HstRequest request, HstResponse response, HippoBean hippoBean, SyndFeed feed,
                                final String authorName) {
    List<SyndEntry> entries = new ArrayList<SyndEntry>();

    if (hippoBean instanceof HippoFacetChildNavigationBean) {
      HippoFacetChildNavigationBean facetNav = (HippoFacetChildNavigationBean) hippoBean;
      HippoDocumentIterator<Blogpost> it = facetNav.getResultSet().getDocumentIterator(Blogpost.class);
      handleEntries(entries, it, request, response, authorName);
    } else {
      try {
        HstQuery hstQuery = getQueryManager().createQuery(hippoBean, Blogpost.class);
        hstQuery.addOrderByDescending(BeanConstants.PROP_DATE);
        HstQueryResult result = hstQuery.execute();
        HippoBeanIterator beans = result.getHippoBeans();
        handleEntries(entries, beans, request, response, authorName);
      } catch (QueryException e) {
        log.warn("Error in querying blogposts, no entries for the feed", e);
      }
    }

    feed.setEntries(entries);
  }

  /**
   * Sets all the metadata for the {@link SyndFeed} (title, author, description etc)
   *
   * @param request    current {@link HstRequest}
   * @param response   {@link HstResponse}
   * @param hippoBean  {@link HippoBean} for the current request
   * @param feed       {@link SyndFeed} to set the metadata
   * @param authorName Author of the feed
   */
  protected void setFeedMetadata(HstRequest request, HstResponse response, HippoBean hippoBean, SyndFeed feed,
                                 final String authorName) {
    String feedType = getPublicRequestParameter(request, FEEDTYPE_PARAM);
    feed.setFeedType(RSS_VALUE.equals(feedType) ? RSS_FEED_TYPE : DEFAULT_FEED_TYPE);

    String title = getParameter(PARAM_TITLE, request);
    feed.setTitle(StringUtils.isBlank(title) ? DEFAULT_FEED_TITLE : title);

    if (StringUtils.isNotBlank(authorName)) {
      feed.setAuthor(authorName);
    }

    String copyright = getParameter(PARAM_COPYRIGHT, request);
    if (StringUtils.isNotBlank(copyright)) {
      feed.setCopyright(copyright);
    }

    String description = getParameter(PARAM_DESCRIPTION, request);
    feed.setDescription(StringUtils.isBlank(description) ? DEFAULT_FEED_DESCRIPTION : description);
    feed.setPublishedDate(new Date());

    ResolvedSiteMapItem currentSiteMapItem = request.getRequestContext().getResolvedSiteMapItem();

    setFeedLinkInformation(request, response, hippoBean, feed, currentSiteMapItem);
  }

  /**
   * Sets both the link to the corresponding webpage and the URI of the feed.
   *
   * @param request            current {@link org.hippoecm.hst.core.component.HstRequest}
   * @param response           {@link org.hippoecm.hst.core.component.HstResponse}
   * @param hippoBean          {@link org.hippoecm.hst.content.beans.standard.HippoBean} for the current request
   * @param feed               {@link com.sun.syndication.feed.synd.SyndFeed} that is being creted for this request
   * @param currentSiteMapItem {@link org.hippoecm.hst.core.request.ResolvedSiteMapItem} for the current request
   */
  private void setFeedLinkInformation(HstRequest request, HstResponse response, HippoBean hippoBean, SyndFeed feed,
                                      ResolvedSiteMapItem currentSiteMapItem) {
    final HstLink feedLink;
    String preferredPath = getParameter(PARAM_PREFERRED_PATH, request);
    if (StringUtils.isBlank(preferredPath)) {
      feedLink = request.getRequestContext().getHstLinkCreator()
              .createCanonical(hippoBean.getNode(), currentSiteMapItem);
    } else {
      ResolvedSiteMapItem preferredItem = request.getRequestContext().getSiteMapMatcher()
              .match(preferredPath, request.getRequestContext().getResolvedMount());

      feedLink = request.getRequestContext().getHstLinkCreator()
              .createCanonical(hippoBean.getNode(), currentSiteMapItem, preferredItem.getHstSiteMapItem());
    }
    feed.setLink(feedLink.toUrlForm(request, response, true));
    feed.setUri(feedLink.toUrlForm(request, response, true));
  }

  /**
   * For each {@link Blogpost} it creates a {@link SyndEntry} that is displayed in the
   * {@link com.sun.syndication.feed.atom.Feed}
   *
   * @param entries  {@link List}
   * @param beans    {@link Iterator} of {@link Blogpost}
   * @param request  {@link HstRequest}
   * @param response {@link HstResponse}
   * @param author   {@link String} with the name of the blog author
   */
  private void handleEntries(List<SyndEntry> entries, @SuppressWarnings("rawtypes") Iterator beans,
                             HstRequest request, HstResponse response, final String author) {
    int results = 0;

    Blogpost blogpost;
    while (beans.hasNext() && results < BlogListing.PAGESIZE) {
      blogpost = (Blogpost) beans.next();
      SyndEntry syndEntry = createFeedEntryFromBlogpost(request, response, author, blogpost);
      if (syndEntry != null) {
        entries.add(syndEntry);
        results++;
      }
    }
  }

  /**
   * Creates a {@link SyndEntry} from a {@link Blogpost}
   *
   * @param request  current {@link HstRequest}
   * @param response {@link HstResponse}
   * @param author   Author of the blog
   * @param blogpost {@link Blogpost} that contains the content for the {@link SyndEntry}
   * @return a {@link SyndEntry} or {@code null} if the {@link Blogpost} is {@code null}
   */
  private SyndEntry createFeedEntryFromBlogpost(HstRequest request, HstResponse response, final String author,
                                                Blogpost blogpost) {

    if (blogpost == null) {
      return null;
    }

    SyndEntry entry = new SyndEntryImpl();

    entry.setTitle(blogpost.getRawTitle());
    final HstLink link = request.getRequestContext().getHstLinkCreator()
            .create(blogpost, request.getRequestContext());
    entry.setLink(link.toUrlForm(request, response, true));
    // blogger id's are from the migrated Blogger.com Blogposts. The URI is maintained so external sources don't see them as new.
    entry.setUri(StringUtils.isBlank(blogpost.getBloggerId()) ? link.toUrlForm(request, response, true) : blogpost
            .getBloggerId());
    entry.setPublishedDate(blogpost.getDate());
    if (StringUtils.isNotBlank(author)) {
      entry.setAuthor(author);
    }
    setEntryCategories(blogpost, entry);

    SyndContent description = new SyndContentImpl();
    description.setType(APPLICATION_XHTML_XML);
    description.setValue(getEntryDescription(blogpost, request, response));
    entry.setDescription(description);

    return entry;
  }

  /**
   * Sets a {@link List} of {@link SyndCategory} element based on the assigned tags in the {@link Blogpost}
   *
   * @param blogpost {@link Blogpost} with tags
   * @param entry    {@link SyndEntry}
   */
  private void setEntryCategories(Blogpost blogpost, SyndEntry entry) {
    List<SyndCategory> categories = new ArrayList<SyndCategory>();
    for (String tag : blogpost.getTags()) {
      SyndCategory category = new SyndCategoryImpl();
      category.setName(tag);
      categories.add(category);
    }
    entry.setCategories(categories);
  }

  /**
   * Returns a String the HTML content of a {@link Blogpost}
   *
   * @param blogpost {@link Blogpost}
   * @param request  {@link HstRequest}
   * @param response {@link HstResponse}
   * @return String of the HTML content, {@code null} if absent or empty
   */
  private String getEntryDescription(Blogpost blogpost, HstRequest request, HstResponse response) {
    StringBuffer fullContent = new StringBuffer(XHTML_TAG_DIV_START);
    if (StringUtils.isNotBlank(blogpost.getSummary())) {
      fullContent.append(HTML_TAG_P_START);
      fullContent.append(StringEscapeUtils.escapeXml(blogpost.getSummary()));
      fullContent.append(HTML_TAG_P_END);
    }
    fullContent.append(getDescriptionHtmlContent(blogpost, request, response));
    fullContent.append(HTML_TAG_DIV_END);
    return fullContent.toString();
  }

  /**
   * Creates the rewritten HTML content for the description field of a {@link SyndEntry}. Returns an empty {@link String}
   * if there is no HTML content.
   *
   * @param blogpost {@link Blogpost} with the HTML content
   * @param request  current {@link HstRequest}
   * @param response {@link HstResponse}
   * @return HTML content of the description field with all internal links rewritten as external URLs
   */
  private String getDescriptionHtmlContent(Blogpost blogpost, HstRequest request, HstResponse response) {
    HippoHtml hippoHtml = blogpost.getHtml();
    if (hippoHtml == null || hippoHtml.getNode() == null) {
      if (log.isDebugEnabled()) {
        log.debug(blogpost.getName() + " has no HTML node");
      }
      return StringUtils.EMPTY;
    }
    String htmlContent = blogpost.getHtml().getContent();
    if (StringUtils.isBlank(htmlContent)) {
      if (log.isDebugEnabled()) {
        log.debug(blogpost.getName() + " has no or empty HTML content");
      }
      return StringUtils.EMPTY;
    }
    ContentRewriterImpl contentRewriter = new ContentRewriterImpl();
    return contentRewriter.rewriteToExternal(htmlContent, hippoHtml.getNode(), request.getRequestContext());
  }

}
