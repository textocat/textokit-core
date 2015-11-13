/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import java.util.EventListener;

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Wordform;

/**
 * @author Rinat Gareev
 * 
 */
public interface MorphDictionaryListener extends EventListener {

	void onGramModelSet(MorphDictionary dict);

	void onWordformAdded(MorphDictionary dict, String wfString, Wordform wf);
}
