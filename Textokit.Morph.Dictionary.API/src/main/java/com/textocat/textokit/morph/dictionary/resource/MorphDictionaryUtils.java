/**
 *
 */
package com.textocat.textokit.morph.dictionary.resource;

import java.util.BitSet;

/**
 * Operations extending MorphDictionary interface
 *
 * @author Rinat Gareev
 */
public class MorphDictionaryUtils {

    public static BitSet toGramBits(GramModel gm, Iterable<String> grams) {
        BitSet result = new BitSet(gm.getGrammemMaxNumId());
        for (String gr : grams) {
            result.set(gm.getGrammemNumId(gr));
        }
        return result;
    }

    private MorphDictionaryUtils() {
    }

}