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

package com.textocat.textokit.morph.commons;

import java.io.File;

/**
 * @author Rinat Gareev
 */
public class TestUtils {

    public static final String SYSPROP_DICTIONARY_HOME = "opencorpora.home";
    public static final String FILENAME_SERIALIZED_DICTIONARY = "dict.opcorpora.ser";

    static File getSerializedDictionaryFile() {
        String dictHomePath = System.getProperty(SYSPROP_DICTIONARY_HOME);
        if (dictHomePath == null) {
            String errMsg = String.format("Setup '%s' system property", SYSPROP_DICTIONARY_HOME);
            System.err.println(errMsg);
            throw new IllegalStateException(errMsg);
        }
        File dictHomeDir = new File(dictHomePath);
        if (!dictHomeDir.isDirectory()) {
            String errMsg = String.format("%s is not an existing directory", dictHomeDir);
            System.err.println(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return new File(dictHomeDir, FILENAME_SERIALIZED_DICTIONARY);
    }

    private TestUtils() {
    }
}
