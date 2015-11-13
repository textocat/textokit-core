/**
 * 
 */
package ru.ksu.niimm.cll.uima.morph.opencorpora.resource;

/**
 * @author Rinat Gareev
 * 
 */
public interface GramModelPostProcessor {

	void postprocess(ImmutableGramModel.Builder gmBuilder);

}
