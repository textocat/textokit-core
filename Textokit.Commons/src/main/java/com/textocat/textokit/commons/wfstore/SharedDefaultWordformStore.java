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

import org.apache.commons.lang3.SerializationUtils;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author Rinat Gareev
 */
public class SharedDefaultWordformStore<TagType> extends DefaultWordformStore<TagType> implements
        SharedResourceObject {

    private static final long serialVersionUID = 7266695078951639418L;

    @SuppressWarnings("unchecked")
    @Override
    public void load(DataResource dr) throws ResourceInitializationException {
        DefaultWordformStore<TagType> ws;
        try {
            ws = (DefaultWordformStore<TagType>) SerializationUtils.deserialize(
                    new BufferedInputStream(dr.getInputStream()));
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
        this.strKeyMap = ws.strKeyMap;
        this.metadataMap = ws.metadataMap;
    }

}