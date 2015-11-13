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

import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.LemmaLinkType;
import com.textocat.textokit.morph.model.Wordform;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * @author Rinat Gareev
 */
public interface MorphDictionary {

    String getVersion();

    String getRevision();

    GramModel getGramModel();

    List<Wordform> getEntries(String str);

    LemmaLinkType getLemmaLinkType(short id);

    /**
     * @param lemmaId
     * @return lemma with given id
     * @throws IllegalStateException if lemma with given id is not found
     */
    Lemma getLemma(int lemmaId);

    // TODO move to a interface that is kind of MutableMorphDictionary
    void addLemma(Lemma lemma);

    int getLemmaMaxId();

    Map<Integer, LemmaLinkType> getLemmaOutlinks(int lemmaId);

    Map<Integer, LemmaLinkType> getLemmaInlinks(int lemmaId);

    /**
     * @param tag
     * @return true if this dictionary has the given tag
     */
    boolean containsGramSet(BitSet tag);
}
