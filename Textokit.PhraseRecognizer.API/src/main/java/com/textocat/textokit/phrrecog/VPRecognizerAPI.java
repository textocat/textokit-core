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

package com.textocat.textokit.phrrecog;

import com.textocat.textokit.commons.util.PipelineDescriptorUtils;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.Import;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.Import_impl;

/**
 * A class that provides constants and methods to use a verb phrase recognizer.
 *
 * @author Rinat Gareev
 */
public class VPRecognizerAPI {

    /**
     * A name of type-system description that define types produced by phrase recognizer implementations.
     */
    public static final String TYPESYSTEM_PHRASE_RECOGNIZER = "com.textocat.textokit.phrrecog.ts-phrase-recognizer";

    /**
     * A name of analysis engine description that can be imported. An
     * implementation of VP recognizer should provide its description at this
     * location either in classpath or UIMA datapath.
     */
    public static final String AE_VP_RECOGNIZER = "com.textocat.textokit.phrrecog.vp-recognizer";

    public static TypeSystemDescription getTypeSystemDescription() {
        return TypeSystemDescriptionFactory.createTypeSystemDescription(TYPESYSTEM_PHRASE_RECOGNIZER);
    }

    /**
     * @return import instance. This is preferred way to include the AE into
     * pipeline, especially when a pipeline descriptor is expected to be
     * serialized into XML.
     * @see PipelineDescriptorUtils#createAggregateDescription(java.util.List,
     * java.util.List)
     */
    public static Import getAEImport() {
        Import result = new Import_impl();
        result.setName(AE_VP_RECOGNIZER);
        return result;
    }

    private VPRecognizerAPI() {
    }
}
