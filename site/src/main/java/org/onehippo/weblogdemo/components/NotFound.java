package org.onehippo.weblogdemo.components;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Same as {@link Detail} but with a {@value HstResponse.SC_NOT_FOUND} status code
 * @author jashaj
 *
 */
public class NotFound extends Detail {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        response.setStatus(HstResponse.SC_NOT_FOUND);
    }
}
