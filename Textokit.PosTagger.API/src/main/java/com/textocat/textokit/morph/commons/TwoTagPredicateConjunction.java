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

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/**
 * @author Rinat Gareev
 */
public class TwoTagPredicateConjunction implements TwoTagPredicate {

    public static TwoTagPredicateConjunction and(TwoTagPredicate... operands) {
        return new TwoTagPredicateConjunction(Arrays.asList(operands));
    }

    private final List<TwoTagPredicate> operands;

    public TwoTagPredicateConjunction(List<TwoTagPredicate> operands) {
        this.operands = ImmutableList.copyOf(operands);
    }

    @Override
    public boolean apply(BitSet first, BitSet second) {
        for (TwoTagPredicate inner : operands) {
            if (!inner.apply(first, second)) {
                return false;
            }
        }
        return true;
    }

}
