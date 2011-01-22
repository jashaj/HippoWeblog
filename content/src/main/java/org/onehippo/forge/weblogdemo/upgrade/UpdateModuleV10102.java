package org.onehippo.forge.weblogdemo.upgrade;

import org.hippoecm.repository.ext.UpdaterContext;
import org.hippoecm.repository.ext.UpdaterItemVisitor;
import org.hippoecm.repository.ext.UpdaterModule;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

/**
 * Update module for version 1.01.01 to 1.01.02
 *
 * Changes: remove componentclass Aside from HST config
 *
 * @author Jasha Joachimsthal
 */
public class UpdateModuleV10102 implements UpdaterModule{

    public void register(UpdaterContext updaterContext) {
        updaterContext.registerName("weblogdemo-upgrade-v101b");
        updaterContext.registerStartTag("weblogdemo-v101a");
        updaterContext.registerEndTag("weblogdemo-v101b");

        removeAsideComponentClassname(updaterContext, "/hst:configuration/hst:configuration/hst:pages/home/article");
        removeAsideComponentClassname(updaterContext, "/hst:configuration/hst:configuration/hst:components/basepage.aside");

    }

    private void removeAsideComponentClassname(UpdaterContext updaterContext, String path) {
        updaterContext.registerVisitor(new UpdaterItemVisitor.PathVisitor(path) {
            @Override
            protected void leaving(Node node, int level) throws RepositoryException {
                if (node.hasProperty("hst:componentclassname")) {
                    Property property = node.getProperty("hst:componentclassname");
                    if ("org.onehippo.forge.weblogdemo.components.Aside".equals(property.getString())) {
                        property.remove();
                    }
                }
            }
        });
    }
}
