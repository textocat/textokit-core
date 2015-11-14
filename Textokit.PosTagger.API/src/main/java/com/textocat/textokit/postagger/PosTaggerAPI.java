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

package com.textocat.textokit.postagger;

import com.textocat.textokit.commons.util.PipelineDescriptorUtils;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.morph.fs.Wordform;
import com.textocat.textokit.segmentation.SentenceSplitterAPI;
import com.textocat.textokit.tokenizer.TokenizerAPI;
import com.textocat.textokit.tokenizer.fstype.NUM;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.resource.metadata.Import;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.Import_impl;

import static org.apache.uima.fit.factory.ConfigurationParameterFactory.createPrimitiveParameter;

/**
 * A class that provides constants and methods to use a pos-tagger.
 * <p/>
 * Pos-tagger implementation requires CAS with token annotations (see
 * {@link TokenizerAPI}) and sentence annotations (see
 * {@link SentenceSplitterAPI}).
 * <p/>
 * Pos-tagger enriches an input CAS with {@link Word} annotations. Each result
 * {@link Word} should contain at least one {@link Wordform} instance. This
 * {@link Wordform} is supposed to define the most likely interpretation for the
 * underlying token.
 * <p/>
 * A pos-tagger implementation is able to reuse {@link Word} annotations that
 * have been existing in an input CAS. To force this parameter '
 * {@value #PARAM_REUSE_EXISTING_WORD_ANNOTATIONS}' should be set to true (this
 * is not default).
 * <p/>
 * If a pos-tagger implementation needs an external resource with
 * {@link MorphDictionaryHolder} then this resource should be named '
 * {@value #MORPH_DICTIONARY_RESOURCE_NAME}' and be available among resources
 * managed by the comprising pipeline.
 *
 * @author Rinat Gareev
 */
public class PosTaggerAPI {

    /**
     * A name of type-system description that define types produced by
     * pos-tagger implementations.
     */
    public static final String TYPESYSTEM_POSTAGGER = "com.textocat.textokit.morph.morphology-ts";

    /**
     * A name of analysis engine description that can be imported. An
     * implementation of pos-tagger should provide its description at this
     * location either in classpath or UIMA datapath.
     */
    public static final String AE_POSTAGGER = "com.textocat.textokit.postagger.postagger-ae";

    // parameter names and default values (provided only if a parameter is not mandatory)
    public static final String PARAM_REUSE_EXISTING_WORD_ANNOTATIONS = "reuseExistingWordAnnotations";
    public static final String DEFAULT_REUSE_EXISTING_WORD_ANNOTATIONS = "false";
    /**
     * a resource name to declare MorphDictionaryHolder implementation
     */
    public static final String MORPH_DICTIONARY_RESOURCE_NAME = "MorphDictionary";

    /**
     * @return type-system description instance
     */
    public static TypeSystemDescription getTypeSystemDescription() {
        return TypeSystemDescriptionFactory.createTypeSystemDescription(TYPESYSTEM_POSTAGGER);
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
        result.setName(AE_POSTAGGER);
        return result;
    }

    /**
     * Convenience method to create the parameter declaration. For example, it
     * can be used to create parameter overrides in an aggregate descriptor.
     *
     * @return instance with name set to the same value as specified in the
     * pos-tagger description.
     * @see PipelineDescriptorUtils#createOverrideParameterDeclaration(ConfigurationParameter,
     * org.apache.uima.analysis_engine.AnalysisEngineDescription, String,
     * String)
     */
    public static ConfigurationParameter createReuseExistingWordAnnotationParameterDeclaration() {
        return createPrimitiveParameter(PARAM_REUSE_EXISTING_WORD_ANNOTATIONS,
                ConfigurationParameter.TYPE_BOOLEAN,
                null, false, false);
    }

    /*
     * TODO:LOW
     * 1) This is a wrong place for this method
     * 2) Also the semantic of this method forces rather rigid constraints on PosTagger output
     * But until a bright future we have to keep different implementations work in the coherent manner.
     */
    public static boolean canCarryWord(Token token) {
        return token instanceof W || token instanceof NUM;
    }

    private PosTaggerAPI() {
    }
}
