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

import com.textocat.textokit.morph.model.Grammeme;

import java.util.BitSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
public interface GramModel {

    int getGrammemMaxNumId();

    int getGrammemNumId(String gramId);

    Grammeme getGrammem(int numId);

    /**
     * @param id
     * @return grammeme with given string id or null if it does not exist.
     */
    Grammeme getGrammem(String id);

    /**
     * @param gramId
     * @param includeTarget if true given gramId will be included in result set
     * @return bitset containing numerical ids of given grammeme children
     * (including grandchildren and so on). If grammeme with given
     * string id does not exist then result is null.
     */
    BitSet getGrammemWithChildrenBits(String gramId, boolean includeTarget);

    /**
     * @return grammems whose parent id is null.
     */
    Set<String> getTopGrammems();

    /**
     * @param grammems grammem bits
     * @return list of string ids ordered by grammeme numerical id (ascending)
     */
    List<String> toGramSet(BitSet grammems);

    BitSet getPosBits();

    /**
     * @return PoS-label from the given gram bits if there are any; otherwise -
     * null.
     * @throws IllegalArgumentException if there are > 1 PoS-bits set in the given bitset.
     */
    String getPos(BitSet gramBits);
}
