/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import java.util.List;

/**
 * @author Rinat Gareev
 * 
 */
public interface DictionaryExtension {

	List<LemmaPostProcessor> getLexemePostprocessors();

	List<GramModelPostProcessor> getGramModelPostProcessors();
}
