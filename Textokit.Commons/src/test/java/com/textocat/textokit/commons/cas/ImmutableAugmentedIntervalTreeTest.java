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

import com.textocat.textokit.commons.util.OffsetsWithValue;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class ImmutableAugmentedIntervalTreeTest {
    @Test
    public void test() {
        List<OffsetsWithValue<String>> srcIntervals = newArrayList(
                // 0
                new OffsetsWithValue<String>(17, 19, null),
                // 1
                new OffsetsWithValue<>(5, 8, "a"),
                // 2
                new OffsetsWithValue<String>(21, 24, null),
                // 3
                new OffsetsWithValue<>(5, 8, "b"),
                // 4
                new OffsetsWithValue<String>(4, 8, null),
                // 5
                new OffsetsWithValue<String>(15, 18, null),
                // 6
                new OffsetsWithValue<String>(7, 10, null),
                // 7
                new OffsetsWithValue<String>(16, 22, null)
        );
        ImmutableAugmentedIntervalTree<String> tree = new ImmutableAugmentedIntervalTree<>(srcIntervals);
        assertEquals(newArrayList(srcIntervals.get(2)), tree.getOverlapping(23, 25));
        assertEquals(newArrayList(), tree.getOverlapping(12, 14));
        assertEquals(newArrayList(srcIntervals.get(7), srcIntervals.get(2)),
                tree.getOverlapping(21, 23));
        assertEquals(newArrayList(),
                tree.getOverlapping(10, 15));
        assertEquals(newArrayList(srcIntervals.get(4), srcIntervals.get(1), srcIntervals.get(3), srcIntervals.get(6)),
                tree.getOverlapping(5, 8));
    }
}
