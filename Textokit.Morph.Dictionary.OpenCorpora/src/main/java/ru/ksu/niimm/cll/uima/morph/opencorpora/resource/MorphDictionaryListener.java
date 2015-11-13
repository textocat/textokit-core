/**
 * 
 */
package ru.ksu.niimm.cll.uima.morph.opencorpora.resource;

import java.util.EventListener;

import ru.kfu.itis.issst.uima.morph.dictionary.resource.MorphDictionary;
import ru.kfu.itis.issst.uima.morph.model.Wordform;

/**
 * @author Rinat Gareev
 * 
 */
public interface MorphDictionaryListener extends EventListener {

	void onGramModelSet(MorphDictionary dict);

	void onWordformAdded(MorphDictionary dict, String wfString, Wordform wf);
}
