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