/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import org.apache.commons.lang3.StringUtils;

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;

import com.google.common.base.Objects;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * @author Rinat Gareev
 * 
 */
public class YoLemmaPostProcessor extends LexemePostProcessorBase {

	private static final String YO_CHARS = "ёЁ";
	private static final String YO_REPLACEMENTS = "еЕ";

	public static final YoLemmaPostProcessor INSTANCE = new YoLemmaPostProcessor();

	private YoLemmaPostProcessor() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean process(MorphDictionary dict, Lemma.Builder lemmaBuilder,
			Multimap<String, Wordform> wfMap) {
		Multimap<String, Wordform> additionalWfs = LinkedHashMultimap.create();
		for (String wfStr : wfMap.keySet()) {
			// alternative wordform string
			String altStr = StringUtils.replaceChars(wfStr, YO_CHARS, YO_REPLACEMENTS);
			if (Objects.equal(wfStr, altStr)) {
				continue;
			} // else wfStr contains 'yo'
			if (wfMap.containsKey(altStr)) {
				// the wordform multimap already contains string without 'yo'
				continue;
			}
			additionalWfs.putAll(altStr, wfMap.get(wfStr));
		}
		wfMap.putAll(additionalWfs);
		return true;
	}

}