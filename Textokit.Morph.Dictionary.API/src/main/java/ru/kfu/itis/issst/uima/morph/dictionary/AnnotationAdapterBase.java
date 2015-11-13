/**
 * 
 */
package ru.kfu.itis.issst.uima.morph.dictionary;

import ru.kfu.itis.issst.uima.morph.dictionary.resource.MorphDictionary;

/**
 * @author Rinat Gareev
 * 
 */
public abstract class AnnotationAdapterBase implements AnnotationAdapter {

	protected MorphDictionary dict;

	@Override
	public void init(MorphDictionary dict) {
		this.dict = dict;
	}

}