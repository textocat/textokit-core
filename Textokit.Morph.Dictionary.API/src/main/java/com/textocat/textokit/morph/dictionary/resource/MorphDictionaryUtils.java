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

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.LemmaLinkType;
import com.textocat.textokit.morph.model.Wordform;

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * @param surface        a wordform
     * @param linkTypeFilter a predicate to ignore certain link types
     * @param dict           a dictionary instance
     * @return a set of lemmas linked to each other including one of the given wordform.
     * If the wordform is ambiguous then the result will contain lemmas linked to any interpretation.
     * Return an empty set if the given worform is not found in the dictionary.
     */
    public static Set<Lemma> getLinkedLemmas(String surface,
                                             Predicate<LemmaLinkType> linkTypeFilter,
                                             MorphDictionary dict) {
        List<Wordform> des = dict.getEntries(surface);
        Set<Lemma> res = Sets.newHashSet();
        for (Wordform wf : des) {
            res.addAll(getLinkedLemmas(wf, linkTypeFilter, dict));
        }
        return res;
    }

    public static Set<Lemma> getLinkedLemmas(Wordform wf, Predicate<LemmaLinkType> linkTypeFilter, MorphDictionary dict) {
        Set<Lemma> resultSet = Sets.newHashSet();
        Set<Integer> ignoreSet = Sets.newHashSet();
        // call recursive function
        Lemma wfLemma = dict.getLemma(wf.getLemmaId());
        resultSet.add(wfLemma);
        addLinkedLemmas(wfLemma, linkTypeFilter, resultSet, ignoreSet, dict);
        return resultSet;
    }

    private static void addLinkedLemmas(Lemma target, Predicate<LemmaLinkType> linkFilter,
                                        Set<Lemma> resultSet, Set<Integer> ignoreSet, MorphDictionary dict) {
        ignoreSet.add(target.getId());
        //
        Set<Lemma> linkedLemmas = Sets.newHashSet();
        linkedLemmas.addAll(getLemmasById(dict.getLemmaInlinks(target.getId()), linkFilter, dict));
        linkedLemmas.addAll(getLemmasById(dict.getLemmaOutlinks(target.getId()), linkFilter, dict));
        resultSet.addAll(linkedLemmas);
        // recursive calls
        for (Lemma ll : linkedLemmas) {
            if (!ignoreSet.contains(ll.getId())) {
                addLinkedLemmas(ll, linkFilter, resultSet, ignoreSet, dict);
            }
        }
    }

    private static Set<Lemma> getLemmasById(Map<Integer, LemmaLinkType> linkMap,
                                            Predicate<LemmaLinkType> linkTypeFilter,
                                            MorphDictionary dict) {
        Set<Lemma> res = Sets.newHashSet();
        for (Map.Entry<Integer, LemmaLinkType> lme : linkMap.entrySet()) {
            if (linkTypeFilter.apply(lme.getValue())) {
                Lemma l = dict.getLemma(lme.getKey());
                res.add(l);
            }
        }
        return res;
    }

    private MorphDictionaryUtils() {
    }

}