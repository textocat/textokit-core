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

package com.textocat.textokit.eval.matching;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Rinat Gareev
 */
class CollectionMatcher<V, C extends Collection<V>> implements Matcher<C> {

    private boolean ignoreOrder;
    private Matcher<V> elemMatcher;

    public CollectionMatcher(Matcher<V> elemMatcher, boolean ignoreOrder) {
        this.ignoreOrder = ignoreOrder;
        this.elemMatcher = elemMatcher;
    }

    @Override
    public boolean match(C refCol, C candCol) {
        if (refCol == null) {
            return candCol == null;
        }
        if (candCol == null) {
            return false;
        }
        if (refCol.size() != candCol.size()) {
            return false;
        }
        if (!ignoreOrder) {
            Iterator<V> candIter = candCol.iterator();
            for (V refElem : refCol) {
                V candElem = candIter.next();
                if (!elemMatcher.match(refElem, candElem)) {
                    return false;
                }
            }
        } else {
            // order should be ignored
            List<V> nonMatchedCandElems = newArrayList(candCol);
            for (V refElem : refCol) {
                int matchedCandIndex = search(refElem, nonMatchedCandElems);
                if (matchedCandIndex < 0) {
                    return false;
                }
                nonMatchedCandElems.remove(matchedCandIndex);
            }
        }
        return true;
    }

    @Override
    public void print(StringBuilder out, C col) {
        if (col == null) {
            out.append((Object) null);
        } else {
            out.append("{");
            Iterator<V> iter = col.iterator();
            if (iter.hasNext()) {
                elemMatcher.print(out, iter.next());
            }
            while (iter.hasNext()) {
                out.append(",");
                elemMatcher.print(out, iter.next());
            }
            out.append("}");
        }
    }

    private int search(V refElem, List<V> candidatesList) {
        for (int i = 0; i < candidatesList.size(); i++) {
            V cand = candidatesList.get(i);
            if (elemMatcher.match(refElem, cand)) {
                return i;
            }
        }
        return -1;
    }
}