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

import com.google.common.base.Preconditions;
import org.apache.uima.UIMAFramework;
import org.apache.uima.resource.ResourceManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.String.format;

/**
 * @author Rinat Gareev
 */
public class UimaResourceUtils {

    private UimaResourceUtils() {
    }

    public static File resolveFile(String path, ResourceManager resMgr)
            throws URISyntaxException, MalformedURLException {
        Preconditions.checkArgument(path != null);
        if (resMgr == null) {
            resMgr = UIMAFramework.newDefaultResourceManager();
        }
        URL modelBaseURL = resMgr.resolveRelativePath(path);
        if (modelBaseURL == null)
            throw new IllegalStateException(format(
                    "Can't resolve path %s using an UIMA relative path resolver", path));
        return new File(modelBaseURL.toURI());
    }

    public static File resolveDirectory(String path, ResourceManager resMgr)
            throws MalformedURLException, URISyntaxException {
        File f = resolveFile(path, resMgr);
        if (!f.isDirectory()) {
            throw new IllegalStateException(format(
                    "Path '%s' is resolved into '%s' but it is not a directory", path, f));
        }
        return f;
    }
}
