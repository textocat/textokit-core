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

package com.textocat.textokit.morph.opencorpora.resource;

import com.google.common.collect.Multimap;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;

/**
 * For test purposes ONLY!
 *
 * @author Rinat Gareev
 */
public class OddLemmaFilter extends LexemePostProcessorBase {

    @Override
    public boolean process(MorphDictionary dict, Lemma.Builder lemma,
                           Multimap<String, Wordform> wfMap) {
        return lemma.getLemmaId() % 10 == 0;
    }

}