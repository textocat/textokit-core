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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;

import static java.lang.System.currentTimeMillis;

/**
 * @author Rinat Gareev
 */
public class GramModelDeserializer {

    private static final Logger log = LoggerFactory.getLogger(GramModelDeserializer.class);

    public static GramModel from(File file) throws Exception {
        return from(FileUtils.openInputStream(file), file.toString());
    }

    public static GramModel from(InputStream in, String srcLabel) throws Exception {
        log.info("About to deserialize GramModel from InputStream of {}...", srcLabel);
        long timeBefore = currentTimeMillis();
        InputStream is = new BufferedInputStream(in);
        ObjectInputStream ois = new ObjectInputStream(is);
        GramModel gm;
        try {
            gm = (GramModel) ois.readObject();
        } finally {
            IOUtils.closeQuietly(ois);
        }
        log.info("Deserialization of GramModel finished in {} ms",
                currentTimeMillis() - timeBefore);
        return gm;
    }

    private GramModelDeserializer() {
    }

}
