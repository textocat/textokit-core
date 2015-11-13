/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Wordform;
import com.textocat.textokit.morph.model.Lemma.Builder;

import com.google.common.collect.Multimap;

/**
 * @author Rinat Gareev
 * 
 */
public class LexemePostProcessorBase implements LemmaPostProcessor {

	@Override
	public boolean process(MorphDictionary dict, Builder lemmaBuilder,
			Multimap<String, Wordform> wfMap) {
		return true;
	}

	@Override
	public void dictionaryParsed(MorphDictionary dict) {
	}

}
