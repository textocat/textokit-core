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
package com.textocat.textokit.segmentation;

import com.textocat.textokit.segmentation.fstype.Sentence;
import com.textocat.textokit.tokenizer.TokenizerAPI;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.Import;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.Import_impl;

import java.io.IOException;

/**
 * A class that provides constants and methods to use a sentence-splitter.
 * <p/>
 * By default an implementation of sentence splitter requires an input CAS with
 * token annotations. See {@link TokenizerAPI}.
 * <p/>
 * Sentence splitter enriches a CAS with {@link Sentence} annotations.
 *
 * @author Rinat Gareev
 */
public class SentenceSplitterAPI {

    /**
     * A name of analysis engine description that can be imported. An
     * implementation of sentence-splitter should provide its description at
     * this location either in classpath or UIMA datapath.
     */
    public static final String AE_SENTENCE_SPLITTER = "com.textocat.textokit.segmentation.sentence-splitter-ae";

    /**
     * A name of type-system description that define types produced by
     * implementations of sentence splitter.
     */
    public static final String TYPESYSTEM_SENTENCES = "com.textocat.textokit.segmentation.segmentation-TypeSystem";

    /**
     * @return type-system description instance
     */
    public static TypeSystemDescription getTypeSystemDescription() {
        return TypeSystemDescriptionFactory.createTypeSystemDescription(TYPESYSTEM_SENTENCES);
    }

    /**
     * @return AE description instance
     * @throws UIMAException
     * @throws IOException
     */
    public static AnalysisEngineDescription getAEDescription() throws UIMAException, IOException {
        return AnalysisEngineFactory.createEngineDescription(AE_SENTENCE_SPLITTER);
    }

    /**
     * @return import instance. This is preferred way to include the AE into
     * pipeline, especially when a pipeline descriptor is expected to be
     * serialized into XML.
     * @see com.textocat.textokit.commons.util.PipelineDescriptorUtils#createAggregateDescription(java.util.List,
     * java.util.List)
     */
    public static Import getAEImport() {
        Import result = new Import_impl();
        result.setName(AE_SENTENCE_SPLITTER);
        return result;
    }

    private SentenceSplitterAPI() {
    }

}
