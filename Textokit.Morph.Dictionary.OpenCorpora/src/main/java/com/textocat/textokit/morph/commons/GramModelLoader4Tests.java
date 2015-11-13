/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
