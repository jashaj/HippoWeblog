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
package org.onehippo.weblogdemo.beans;

/**
 * Utility class that contains constants for all document types and their fields
 * @author Jasha Joachimsthal
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
