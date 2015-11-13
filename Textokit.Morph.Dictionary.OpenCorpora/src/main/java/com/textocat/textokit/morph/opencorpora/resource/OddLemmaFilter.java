/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.google.common.collect.Multimap;

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;

/**
 * For test purposes ONLY!
 * 
 * @author Rinat Gareev
 * 
 */
public class OddLemmaFilter extends LexemePostProcessorBase {

	@Override
	public boolean process(MorphDictionary dict, Lemma.Builder lemma,
			Multimap<String, Wordform> wfMap) {
		return lemma.getLemmaId() % 10 == 0;
	}

}