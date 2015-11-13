/**
 *
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Grammeme;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;

import java.util.BitSet;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class LemmaByGrammemFilter extends LexemePostProcessorBase {

    private Set<String> grammemsToReject;

    public LemmaByGrammemFilter(String... grammemsToReject) {
        this.grammemsToReject = ImmutableSet.copyOf(grammemsToReject);
    }

    @Override
    public boolean process(MorphDictionary dict, Lemma.Builder lemmaBuilder,
                           Multimap<String, Wordform> wfMap) {
        BitSet grBits = lemmaBuilder.getGrammems();
        for (int i = grBits.nextSetBit(0); i >= 0; i = grBits.nextSetBit(i + 1)) {
            Grammeme gr = dict.getGramModel().getGrammem(i);
            if (grammemsToReject.contains(gr.getId())) {
                return false;
            }
        }
        return true;
    }

}