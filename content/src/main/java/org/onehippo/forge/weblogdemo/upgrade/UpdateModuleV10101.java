package org.onehippo.forge.weblogdemo.upgrade;

import org.hippoecm.repository.ext.UpdaterContext;
import org.hippoecm.repository.ext.UpdaterItemVisitor;
import org.hippoecm.repository.ext.UpdaterModule;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

/**
 * Update module for version 1.01.00 to 1.01.01
 *
 * Changes: fix path for atom feed template in HST config
 *
 * @author Jasha Joachimsthal
 */
public class UpdateModuleV10101 implements UpdaterModule {
    public void register(UpdaterContext updaterContext) {
        updaterContext.registerName("weblogdemo-upgrade-v101");
        updaterContext.registerStartTag("weblogdemo-v101");
        updaterContext.registerEndTag("weblogdemo-v101a");

        updaterContext.registerVisitor(new UpdaterItemVisitor.PathVisitor("/hst:configuration/hst:configuration/hst:templates/feed.atom") {
            @Override
            protected void leaving(Node node, int level) throws RepositoryException {
                if (node.hasProperty("hst:renderpath")) {
                    Property property = node.getProperty("hst:renderpath");
                    if ("/WEB-INF/jsp/feeds/atom.ftl".equals(property.getString())) {
                        property.setValue("/WEB-INF/jsp/feeds/atom.jsp");
                    }
                }
            }
        });


    }
}
