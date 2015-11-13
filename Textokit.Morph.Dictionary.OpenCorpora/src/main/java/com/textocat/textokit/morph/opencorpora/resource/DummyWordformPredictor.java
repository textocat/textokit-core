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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DummyWordformPredictor implements WordformPredictor {
    private MorphDictionary dict;
    private int pseudoLemmaIdCounter = -1;
    private Map<Lemma, Lemma> uniqPseudoLemmaMap = Maps.newHashMap();

    private synchronized Lemma addPseudoLemma(Lemma l) {
        if (uniqPseudoLemmaMap.containsKey(l)) {
            return uniqPseudoLemmaMap.get(l);
        } else {
            l.setId(pseudoLemmaIdCounter--);
            uniqPseudoLemmaMap.put(l, l);
            dict.addLemma(l);
            return l;
        }
    }

    public DummyWordformPredictor(MorphDictionary dict) {
        this.dict = dict;

    }

    @Override
    public List<Wordform> predict(String str, WordformTSTSearchResult result) {
        Set<Wordform> wfSet = new HashSet<Wordform>();
        for (Wordform wf : result) {
            Lemma lemma = dict.getLemma(wf.getLemmaId()).cloneWithoutIdAndString();
            lemma = addPseudoLemma(lemma);

            wf = wf.cloneWithLemmaId(lemma.getId());
            wfSet.add(wf);
        }
        return Lists.newArrayList(wfSet);
    }
}
