/**
 *
 */
package com.textocat.textokit.morph.commons;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.opencorpora.resource.DictionaryDeserializer;

/**
 * @author Rinat Gareev
 */
public class DictionaryLoader4Tests {

    public static final MorphDictionary dict;
    public static final GramModel gm;

    static {
        try {
            dict = DictionaryDeserializer.from(TestUtils.getSerializedDictionaryFile());
            gm = dict.getGramModel();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void init() {
        // this is just 'sugar' method
        // initialization will be done when this class is accessed the first time
    }

    private DictionaryLoader4Tests() {
    }
}
