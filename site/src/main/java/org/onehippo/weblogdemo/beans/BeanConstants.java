package org.onehippo.weblogdemo.beans;

/**
 * Utility class that contains constants for all document types and their fields
 * @author jashaj
 *
 */
public class BeanConstants {

    private BeanConstants() {
        // prevent instantiation
    }

    public static final String DOCTYPE_BASEDOCUMENT = "website2010:basedocument";
    public static final String DOCTYPE_BLOGPOST = "website2010:blogpost";
    public static final String DOCTYPE_COMMENT  = "website2010:comment";

    public static final String COMPOUND_BODY = "website2010:body";

    public static final String PROP_BLOGGERID = "website2010:bloggerid";
    public static final String PROP_DATE = "website2010:date";
    public static final String PROP_SUMMARY = "website2010:summary";
    public static final String PROP_TITLE = "website2010:title";
}
