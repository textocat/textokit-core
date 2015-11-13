/**
 * 
 */
package com.textocat.textokit.morph.dictionary;

import java.util.BitSet;
import java.util.Collection;

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Wordform;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * @author Rinat Gareev
 * 
 */
public interface AnnotationAdapter {

	void init(MorphDictionary dict);

	void apply(JCas jcas, Annotation token, Collection<Wordform> wordforms);

	/**
	 * @param jcas
	 * @param token
	 * @param lexemeId
	 * @param lemma
	 *            normal form of lexeme. Must be null if lexemeId is specified.
	 * @param posBits
	 */
	void apply(JCas jcas, Annotation token, Integer lexemeId, String lemma, BitSet posBits);
}