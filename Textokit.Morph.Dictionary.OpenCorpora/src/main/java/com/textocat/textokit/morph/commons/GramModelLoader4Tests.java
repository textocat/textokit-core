/**
 *
 */
package com.textocat.textokit.morph.commons;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.opencorpora.resource.GramModelDeserializer;

/**
 * @author Rinat Gareev
 */
public class GramModelLoader4Tests {

    public static final GramModel gm;

    static {
        try {
            gm = GramModelDeserializer.from(TestUtils.getSerializedDictionaryFile());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void init() {
        // this is just 'sugar' method
        // initialization will be done when this class is accessed the first time
    }

    private GramModelLoader4Tests() {
    }
}
