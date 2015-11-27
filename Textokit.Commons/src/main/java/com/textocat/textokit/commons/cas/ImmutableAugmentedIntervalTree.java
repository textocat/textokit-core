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

import com.google.common.collect.Lists;
import com.textocat.textokit.commons.util.Offsets;
import com.textocat.textokit.commons.util.OffsetsWithValue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * See <a href="http://en.wikipedia.org/wiki/Interval_tree#Augmented_tree">this wiki page</a> for a reference.
 * Note that here all intervals considered to be left-closed right-open as annotations.
 *
 * @author Rinat Gareev
 */
class ImmutableAugmentedIntervalTree<V> {

    public ImmutableAugmentedIntervalTree(List<OffsetsWithValue<V>> intervals) {
        intervals = Lists.newArrayList(intervals);
        // using stable sort to preserve the source ordering
        Collections.sort(intervals, new Comparator<OffsetsWithValue<?>>() {
            @Override
            public int compare(OffsetsWithValue o1, OffsetsWithValue o2) {
                if (o1.getBegin() > o2.getBegin()) {
                    return 1;
                } else if (o1.getBegin() < o2.getBegin()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        root = new IntervalNode(intervals);
    }

    public List<OffsetsWithValue<V>> getOverlapping(int begin, int end) {
        List<OffsetsWithValue<V>> resultList = Lists.newLinkedList();
        search(root, new Offsets(begin, end), resultList);
        return resultList;
    }

    private final IntervalNode root;

    private class IntervalNode {
        final IntervalNode left;
        final OffsetsWithValue<V> offsets;
        final int maxEnd;
        final IntervalNode right;

        // NOTE! pass only sorted lists
        private IntervalNode(List<OffsetsWithValue<V>> intervals) {
            this.maxEnd = findMaxEnd(intervals);
            if (intervals.size() == 1) {
                this.left = null;
                this.offsets = intervals.get(0);
                this.right = null;
            } else if (intervals.size() == 2) {
                // putting the first elem to the left allows to preserve source ordering
                this.left = new IntervalNode(intervals.subList(0, 1));
                this.offsets = intervals.get(1);
                this.right = null;
            } else {
                int medianIndex = intervals.size() / 2; // we get + 1 to actual index as indexes are 0-based
                //
                this.left = new IntervalNode(intervals.subList(0, medianIndex));
                this.offsets = intervals.get(medianIndex);
                this.right = new IntervalNode(intervals.subList(medianIndex + 1, intervals.size()));
            }
        }
    }

    private static int findMaxEnd(List<? extends Offsets> intervals) {
        if (intervals.isEmpty()) throw new IllegalArgumentException();
        int max = Integer.MIN_VALUE;
        for (Offsets o : intervals) {
            if (o.getEnd() > max) {
                max = o.getEnd();
            }
        }
        return max;
    }

    private void search(IntervalNode n, Offsets query, List<OffsetsWithValue<V>> resultList) {
        // Don't search nodes that don't exist
        if (n == null)
            return;

        // If query is to the right of the rightmost point of any interval
        // in this node and all children, there won't be any matches.
        if (query.getBegin() >= n.maxEnd) {
            return;
        }

        // Search left children
        search(n.left, query, resultList);

        // Check this node
        if (query.overlaps(n.offsets)) {
            resultList.add(n.offsets);
        }

        // If query is to the left of the start of this interval,
        // then it can't be in any child to the right.
        if (query.getEnd() <= n.offsets.getBegin()) {
            return;
        }

        // Otherwise, search right children
        search(n.right, query, resultList);
    }
}
