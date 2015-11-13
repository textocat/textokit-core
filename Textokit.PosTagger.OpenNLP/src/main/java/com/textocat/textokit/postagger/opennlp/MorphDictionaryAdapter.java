/**
 *
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
