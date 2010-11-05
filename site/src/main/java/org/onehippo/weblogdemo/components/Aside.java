package org.onehippo.weblogdemo.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class Aside extends BaseSiteComponent {

    
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {     // dynamically included children
        if (response.getChildContentNames() != null && response.getChildContentNames().size() > 0) {
            List<String> childNames = new ArrayList<String>();
            childNames.addAll(response.getChildContentNames());
            Collections.sort(childNames);
            request.setAttribute("includes", childNames);
        }

    }
}
