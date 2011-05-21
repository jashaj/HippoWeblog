package org.onehippo.forge.weblogdemo.upgrade;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.query.Query;

import org.apache.jackrabbit.value.StringValue;
import org.hippoecm.repository.ext.UpdaterContext;
import org.hippoecm.repository.ext.UpdaterItemVisitor;
import org.hippoecm.repository.ext.UpdaterModule;

/**
 * Updater for version 1.01.02 (Hippo CMS 7.4 / HST 2.05)
 * to 1.02.00 (Hippo CMS 7.6 / HST 2.20)
 */
public class UpdateModuleV10200 implements UpdaterModule {

  @Override
  public void register(UpdaterContext updaterContext) {
    updaterContext.registerName("weblogdemo-upgrade-v102a");
    updaterContext.registerStartTag("weblogdemo-v101b");
    updaterContext.registerEndTag("weblogdemo-v102a");
    updaterContext.registerAfter("v19a");

    removeOldInitializers(updaterContext);
    removeFacetSearchTags(updaterContext);
    updateGalleryType(updaterContext);
  }

  /**
   * Removes initialize nodes that should no longer be used
   *
   * @param updaterContext {@link UpdaterContext}
   */
  private void removeOldInitializers(UpdaterContext updaterContext) {
    updaterContext.registerVisitor(new UpdaterItemVisitor.PathVisitor("/hippo:configuration/hippo:initialize") {
      @Override
      protected void leaving(Node node, int level) throws RepositoryException {
        removeChildNodeIfExists(node, "hst-live");
        removeChildNodeIfExists(node, "hst-preview");
        removeChildNodeIfExists(node, "tagging-facetsearch");
        removeChildNodeIfExists(node, "custom-browserPerspective");
        removeChildNodeIfExists(node, "virtualhosts");
        removeChildNodeIfExists(node, "custom-thumbnail-size");

      }
    });
  }

  /**
   * Updates existing content to use the new hippogallery:imageset
   *
   * @param updaterContext {@link UpdaterContext}
   */
  private void updateGalleryType(UpdaterContext updaterContext) {
    updaterContext.registerVisitor(new UpdaterItemVisitor.PathVisitor("/content/gallery") {
      @Override
      protected void leaving(Node node, int level) throws RepositoryException {
        if (!node.hasProperty("hippostd:gallerytype")) {
          return;
        }
        Property property = node.getProperty("hippostd:gallerytype");
        Value[] values = property.getValues();
        List<Value> newValues = new ArrayList<Value>();
        for (Value value : values) {
          if ("hippogallery:exampleImageSet".equals(value)) {
            newValues.add(new StringValue("hippogallery:imageset"));
          } else {
            newValues.add(value);
          }
        }
        property.setValue(newValues.toArray(new Value[newValues.size()]));
      }
    });

    updaterContext.registerVisitor(
            new UpdaterItemVisitor.QueryVisitor("//content/documents//*[@jcr:primaryType='hippostd:html']",
                    Query.XPATH) {
              @Override
              protected void leaving(Node node, int level) throws RepositoryException {
                Property property = node.getProperty("hippostd:content");

                String value = property.getString();
                if (value != null) {
                  String newValue = value.replaceAll("hippogallery:picture", "hippogallery:original");
                  property.setValue(newValue);
                }
              }
            });
  }

  /**
   * Deletes the existing /tags Node which is changed from type facetsearch to facetselect
   *
   * @param updaterContext {@link UpdaterContext}
   */
  private void removeFacetSearchTags(UpdaterContext updaterContext) {
    updaterContext.registerVisitor(new UpdaterItemVisitor.PathVisitor("/tags") {
      @Override
      protected void leaving(Node node, int level) throws RepositoryException {
        node.remove();
      }
    });
  }

  private void removeChildNodeIfExists(Node node, String childToDelete) throws RepositoryException {
    if (node.hasNode(childToDelete)) {
      Node removeChild = node.getNode(childToDelete);
      removeChild.remove();
    }
  }
}
