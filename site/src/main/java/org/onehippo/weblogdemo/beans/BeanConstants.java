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

    public static final String DOCTYPE_BASEDOCUMENT = "weblogdemo:basedocument";
    public static final String DOCTYPE_BLOGPOST = "weblogdemo:blogpost";
    public static final String DOCTYPE_COMMENT  = "weblogdemo:comment";

    public static final String COMPOUND_BODY = "weblogdemo:body";

    public static final String PROP_BLOGGERID = "weblogdemo:bloggerid";
    public static final String PROP_DATE = "weblogdemo:date";
    public static final String PROP_SUMMARY = "weblogdemo:summary";
    public static final String PROP_TITLE = "weblogdemo:title";
}
