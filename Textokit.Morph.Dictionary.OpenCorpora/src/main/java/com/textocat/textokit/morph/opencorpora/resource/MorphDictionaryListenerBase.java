/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Wordform;

/**
 * @author Rinat Gareev
 * 
 */
public class MorphDictionaryListenerBase implements MorphDictionaryListener {

	@Override
	public void onGramModelSet(MorphDictionary dict) {
	}

	@Override
	public void onWordformAdded(MorphDictionary dict, String wfString, Wordform wf) {
	}

}
