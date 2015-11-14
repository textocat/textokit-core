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

package com.textocat.textokit.commons.util;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Rinat Gareev
 */
public class ManifestUtils {

    /**
     * @return manifests that contains a main attribute with the specified key
     */
    public static List<Manifest> searchByAttributeKey(String key) {
        try {
            Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            List<Manifest> resultList = Lists.newLinkedList();
            while (resEnum.hasMoreElements()) {
                URL url = resEnum.nextElement();
                try (InputStream is = url.openStream()) {
                    Manifest manifest = new Manifest(is);
                    Attributes mainAttribs = manifest.getMainAttributes();
                    if (mainAttribs.getValue(key) != null) {
                        resultList.add(manifest);
                    }
                }
            }
            return resultList;
        } catch (IOException e) {
            throw new IllegalStateException("manifest lookup error", e);
        }
    }

    private ManifestUtils() {
    }
}
