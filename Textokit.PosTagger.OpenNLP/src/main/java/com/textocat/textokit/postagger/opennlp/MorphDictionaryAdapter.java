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

package com.textocat.textokit.postagger.opennlp;

import com.google.common.collect.ImmutableSet;
import com.textocat.textokit.morph.dictionary.WordUtils;
import opennlp.tools.postag.TagDictionary;
import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class MorphDictionaryAdapter implements TagDictionary {

    public static final String RESOURCE_MORPH_DICTIONARY = "morphDict";
    // XXX
    static final String PARAM_GRAM_CATEGORIES = "gram.categories";

    @ExternalResource()
    private Set<String> gramCategories;

    public MorphDictionaryAdapter(UimaContext ctx) throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(ctx, ctx);
    }

    @Override
    public String[] getTags(String word) {
        word = WordUtils.normalizeToDictionaryForm(word);
        // XXX
        throw new UnsupportedOperationException();
    }

    public Set<String> getGramCategories() {
        return ImmutableSet.copyOf(gramCategories);
    }
}
