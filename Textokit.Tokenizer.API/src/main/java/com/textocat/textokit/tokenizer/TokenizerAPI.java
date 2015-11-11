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
package com.textocat.textokit.tokenizer;

import com.textocat.textokit.tokenizer.fstype.TokenBase;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.Import;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.Import_impl;

import java.io.IOException;

/**
 * A class that provides constants and methods to use a tokenizer.
 * <p/>
 * Any tokenizer implementation requires CAS with document text set. Tokenizer
 * enriches an input CAS with token annotations (the root of their hierarchy is
 * {@link TokenBase}).
 *
 * @author Rinat Gareev
 */
public class TokenizerAPI {

    /**
     * A name of analysis engine description that can be imported. A tokenizer
     * implementation should provide its description at this location either in
     * classpath or UIMA datapath.
     */
    public static final String AE_TOKENIZER = "com.textocat.textokit.tokenizer.tokenizer-ae";

    /**
     * A name of type-system description that define types produced by tokenizer
     * implementations.
     */
    public static final String TYPESYSTEM_TOKENIZER = "com.textocat.textokit.tokenizer.tokenizer-TypeSystem";

    /**
     * A parameter of tokenizer AE that defines types of annotations which
     * covered texts must be tokenized.
     */
    public static final String PARAM_SPAN_TYPE = "spanType";

    /**
     * Default value of {@link #PARAM_SPAN_TYPE}
     */
    public static final String DEFAULT_SPAN_TYPE = "uima.tcas.DocumentAnnotation";

    private TokenizerAPI() {
    }

    /**
     * @return type-system description instance
     */
    public static TypeSystemDescription getTypeSystemDescription() {
        return TypeSystemDescriptionFactory.createTypeSystemDescription(TYPESYSTEM_TOKENIZER);
    }

    /**
     * @return AE description instance
     * @throws UIMAException
     * @throws IOException
     */
    public static AnalysisEngineDescription getAEDescription() throws UIMAException, IOException {
        return AnalysisEngineFactory.createEngineDescription(AE_TOKENIZER);
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
        result.setName(AE_TOKENIZER);
        return result;
    }
}
