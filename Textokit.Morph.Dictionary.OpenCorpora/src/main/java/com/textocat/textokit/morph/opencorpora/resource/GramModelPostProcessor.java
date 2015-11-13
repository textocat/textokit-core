/**
 *
 */
package com.textocat.textokit.morph.opencorpora.resource;

/**
 * @author Rinat Gareev
 */
public interface GramModelPostProcessor {

    void postprocess(ImmutableGramModel.Builder gmBuilder);

}
