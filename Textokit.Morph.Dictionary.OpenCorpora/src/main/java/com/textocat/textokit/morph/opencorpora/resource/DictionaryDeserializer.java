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

package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static java.lang.System.currentTimeMillis;

/**
 * @author Rinat Gareev
 */
public class DictionaryDeserializer {

    private static Logger log = LoggerFactory.getLogger(DictionaryDeserializer.class);
    private static final int DICTIONARY_READING_BUFFER_SIZE = 32768;

    public static MorphDictionaryImpl from(File file) throws Exception {
        if (!file.isFile()) {
            throw new IllegalArgumentException(String.format(
                    "%s is not existing file", file));
        }
        return from(new FileInputStream(file), file.toString());
    }

    public static MorphDictionaryImpl from(InputStream in, String srcLabel) throws Exception {
        log.info("About to deserialize MorphDictionary from InputStream of {}...", srcLabel);
        long timeBefore = currentTimeMillis();
        InputStream is = new BufferedInputStream(in, DICTIONARY_READING_BUFFER_SIZE);
        ObjectInputStream ois = new ObjectInputStream(is);
        MorphDictionaryImpl dict;
        try {
            // skip gram model
            @SuppressWarnings("unused")
            GramModel gm = (GramModel) ois.readObject();
            dict = (MorphDictionaryImpl) ois.readObject();
        } finally {
            IOUtils.closeQuietly(ois);
        }
        log.info("Deserialization of MorphDictionary finished in {} ms",
                currentTimeMillis() - timeBefore);
        return dict;
    }

    private DictionaryDeserializer() {
    }
}