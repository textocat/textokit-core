/**
 *
 */
package com.textocat.textokit.morph.opencorpora.resource;

import java.util.List;

/**
 * @author Rinat Gareev
 */
public class DictionaryExtensionBase implements DictionaryExtension {

    @Override
    public List<LemmaPostProcessor> getLexemePostprocessors() {
        return null;
    }

    @Override
    public List<GramModelPostProcessor> getGramModelPostProcessors() {
        return null;
    }

}
