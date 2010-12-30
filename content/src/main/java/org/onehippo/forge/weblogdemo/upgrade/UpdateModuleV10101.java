/*
 * Copyright 2010 Hippo
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
