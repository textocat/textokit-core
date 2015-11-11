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


package com.textocat.textokit.commons.wfstore;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

import static org.apache.commons.io.FileUtils.openOutputStream;

/**
 * @author Rinat Gareev
 */
public class DefaultWordformStore<TagType> implements WordformStore<TagType>, Serializable {

    private static final long serialVersionUID = 1771570908232250753L;

    protected transient Logger log = LoggerFactory.getLogger(getClass());

    protected Map<String, TagType> strKeyMap;
    protected Map<String, Object> metadataMap;

    @Override
    public TagType getTag(String wf) {
        return strKeyMap.get(wf);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(String key, Class<T> valueClass) {
        if (metadataMap == null) {
            return null;
        }
        return (T) metadataMap.get(key);
    }

    @Override
    public void setProperty(String key, Object value) {
        if (metadataMap == null) {
            metadataMap = Maps.newHashMap();
        }
        metadataMap.put(key, value);
    }

    @Override
    public void persist(File outFile) throws Exception {
        // serialize store object
        ObjectOutputStream modelOS = null;
        try {
            OutputStream os = new BufferedOutputStream(openOutputStream(outFile));
            modelOS = new ObjectOutputStream(os);
            modelOS.writeObject(this);
        } finally {
            IOUtils.closeQuietly(modelOS);
        }
        log.info("Succesfully serialized to {}, size = {} bytes",
                outFile, outFile.length());
    }
}