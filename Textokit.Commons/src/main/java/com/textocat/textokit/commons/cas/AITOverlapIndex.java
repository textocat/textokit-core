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

package com.textocat.textokit.commons.cas;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.textocat.textokit.commons.util.OffsetsWithValue;
import org.apache.uima.cas.text.AnnotationFS;

import java.util.*;

/**
 * @author Rinat Gareev
 */
class AITOverlapIndex<A extends AnnotationFS> implements OverlapIndex<A> {
    private ImmutableAugmentedIntervalTree<A> tree;

    AITOverlapIndex(Iterator<A> srcIter) {
        List<A> srcList = Lists.newArrayList(srcIter);
        Collections.sort(srcList, offsetComp);
        List<OffsetsWithValue<A>> intervals = Lists.transform(srcList, new Function<A, OffsetsWithValue<A>>() {
            @Override
            public OffsetsWithValue<A> apply(A anno) {
                return new OffsetsWithValue<A>(anno.getBegin(), anno.getEnd(), anno);
            }
        });
        tree = new ImmutableAugmentedIntervalTree<>(intervals);
    }

    @Override
    public Set<A> getOverlapping(int begin, int end) {
        Set<A> result = Sets.newLinkedHashSet();
        List<OffsetsWithValue<A>> resultList = tree.getOverlapping(begin, end);
        result.addAll(Lists.transform(resultList, new Function<OffsetsWithValue<A>, A>() {
            @Override
            public A apply(OffsetsWithValue<A> input) {
                return input.getValue();
            }
        }));
        return result;
    }

    private final Comparator<AnnotationFS> offsetComp = AnnotationOffsetComparator
            .instance(AnnotationFS.class);
}
