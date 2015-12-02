/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.textocat.textokit.eval;

/**
 * @author Rinat Gareev
 */
public class ConfigurationKeys {

    public static final String KEY_GOLD_CAS_DIR = "goldCasDirectory";
    public static final String KEY_SYSTEM_CAS_DIR = "systemCasDirectory";
    public static final String KEY_ANNOTATION_TYPES = "annotationTypes";
    public static final String TYPE_SYSTEM_DESC = "typeSystem.description";
    public static final String KEY_TYPE_SYSTEM_DESC_PATHS = TYPE_SYSTEM_DESC + ".paths";
    public static final String KEY_TYPE_SYSTEM_DESC_NAMES = TYPE_SYSTEM_DESC + ".names";
    public static final String DOCUMENT_META = "document.meta";
    public static final String KEY_DOCUMENT_META_TYPE = DOCUMENT_META + ".annotationType";
    public static final String KEY_DOCUMENT_META_URI_FEATURE = DOCUMENT_META + ".uriFeatureName";
    public static final String PREFIX_LISTENER_ID = "listener.";
    public static final String PREFIX_LISTENER_PROPERTY = "listenerProperty.";
    public static final String KEY_MATCHING_CONFIGURATION_TARGET_TYPE = "check.targetTypes";
    public static final String PREFIX_MATCHING_CONFIGURATION = "check.";

    // public static final String

    private ConfigurationKeys() {
    }

}