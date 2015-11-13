/**
 *
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.google.common.collect.Multimap;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;

/**
 * @author Rinat Gareev
 */
public interface LemmaPostProcessor {

    boolean process(MorphDictionary dict, Lemma.Builder lemmaBuilder,
                    Multimap<String, Wordform> wfMap);

    void dictionaryParsed(MorphDictionary dict);
}