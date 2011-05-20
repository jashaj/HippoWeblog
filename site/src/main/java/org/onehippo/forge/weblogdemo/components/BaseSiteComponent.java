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
package org.onehippo.forge.weblogdemo.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.container.ComponentManager;
import org.onehippo.forge.properties.api.PropertiesManager;
import org.onehippo.forge.properties.api.PropertiesUtil;
import org.onehippo.forge.properties.bean.PropertiesBean;

/**
 * Base component for this project.
 *
 * @author Jasha Joachimsthal
 */
public class BaseSiteComponent extends BaseHstComponent {

  /**
   * {@inheritDoc}
   */

  @Override
  public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
    super.doBeforeRender(request, response);
    setComponentLabels(request);
    request.setAttribute("cssClass", getParameter("cssClass", request));
  }

  /**
   * Utility method to retrieve the configured label documents for a component
   *
   * @param request {@link HstRequest}
   */
  protected void setComponentLabels(HstRequest request) {
    final ComponentManager componentManager = this.getDefaultClientComponentManager();
    final PropertiesManager propertiesManager = componentManager.getComponent(PropertiesManager.class.getName());
    final HippoBean baseBean = this.getSiteContentBaseBean(request);

    // retrieve labels documents
    Map<String, String> labels;
    String labelsName = getParameter("labelsName", request);
    if (StringUtils.isNotBlank(labelsName)) {
      String[] split = labelsName.split(",");
      List<String> labelsPaths = new ArrayList<String>(split.length);
      for (String s : split) {
        labelsPaths.add(s.trim());
      }
      final List<PropertiesBean> propertiesBeans = propertiesManager.getPropertiesBeans(labelsPaths, baseBean);
      labels = PropertiesUtil.toMap(propertiesBeans);
    } else {
      final PropertiesBean defaultPropertiesBean = propertiesManager.getPropertiesBean(baseBean);
      labels = PropertiesUtil.toMap(defaultPropertiesBean);
    }

    request.setAttribute("labels", labels);
  }
}
