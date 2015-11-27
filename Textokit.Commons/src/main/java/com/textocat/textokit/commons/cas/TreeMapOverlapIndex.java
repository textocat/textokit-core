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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.uima.cas.text.AnnotationFS;

import java.util.*;

/**
 * TODO test
 *
 * @author Rinat Gareev
 */
@Deprecated
class TreeMapOverlapIndex<A extends AnnotationFS> implements OverlapIndex<A> {
    private final Comparator<AnnotationFS> offsetComp = AnnotationOffsetComparator
            .instance(AnnotationFS.class);
    // IMPL START
    private NavigableMap<Integer, Set<A>> beginIdx;
    private NavigableMap<Integer, Set<A>> endIdx;
    private Map<A, Integer> annoIds;
    private final Comparator<A> innerComparator = new Comparator<A>() {
        @Override
        public int compare(A o1, A o2) {
            int result = offsetComp.compare(o1, o2);
            if (result != 0) {
                return result;
            }
            Integer o1Id = annoIds.get(o1);
            Integer o2Id = annoIds.get(o2);
            return o1Id.compareTo(o2Id);
        }
    };
    private int annoIdCounter;

    private TreeMapOverlapIndex(Iterator<A> srcIter) {
        beginIdx = Maps.newTreeMap();
        endIdx = Maps.newTreeMap();
        annoIds = Maps.newHashMap();
        while (srcIter.hasNext()) {
            A sa = srcIter.next();
            // empty annotations overlap with nothing
            if (AnnotationUtils.isEmpty(sa)) {
                continue;
            }
            // by begin
            {
                int saBegin = sa.getBegin();
                Set<A> sameBeginSet = beginIdx.get(saBegin);
                if (sameBeginSet == null) {
                    // preserve source iterator ordering
                    sameBeginSet = Sets.newLinkedHashSet();
                    beginIdx.put(saBegin, sameBeginSet);
                }
                sameBeginSet.add(sa);
            }
            // by end
            {
                int saEnd = sa.getEnd();
                Set<A> sameEndSet = endIdx.get(saEnd);
                if (sameEndSet == null) {
                    // preserve source iterator ordering
                    sameEndSet = Sets.newLinkedHashSet();
                    endIdx.put(saEnd, sameEndSet);
                }
                sameEndSet.add(sa);
            }
            // this is required for internal comparator to preserve source iterator ordering
            // if offsets are equal
            if (!annoIds.containsKey(sa)) {
                annoIds.put(sa, ++annoIdCounter);
            }
        }
    }

    // factory methods
    static <A extends AnnotationFS> TreeMapOverlapIndex<A> from(Iterator<A> srcIter) {
        return new TreeMapOverlapIndex<A>(srcIter);
    }

    @Override
    public Set<A> getOverlapping(int begin, int end) {
        // TODO this implementation is broken
        // it does not handle cas when one annotations cover another
        Set<A> result = Sets.newTreeSet(innerComparator);
        // annotations whose begin in [begin, end) interval
        NavigableMap<Integer, Set<A>> subByBegin = beginIdx.subMap(begin, true, end, false);
        for (Set<A> annoSet : subByBegin.values()) {
            if (annoSet != null) {
                result.addAll(annoSet);
            }
        }
        // annotations whose end in (begin, end]
        NavigableMap<Integer, Set<A>> subByEnd = endIdx.subMap(begin, false, end, true);
        for (Set<A> annoSet : subByEnd.values()) {
            if (annoSet != null) {
                result.addAll(annoSet);
            }
        }
        return result;
    }
}