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
import java.util.IdentityHashMap;

public abstract class MatcherBase<T> implements Matcher<T> {

    @Override
    public String toString() {
        return toString(new IdentityHashMap<Matcher<?>, Integer>());
    }

    /*
     * to avoid infinite recursion in toString computation
     */
    protected abstract String toString(IdentityHashMap<Matcher<?>, Integer> idMap);

    // for tests
    protected abstract Collection<Matcher<?>> getSubMatchers();

    protected static int getNextId(IdentityHashMap<Matcher<?>, Integer> idMap) {
        if (idMap.isEmpty()) {
            return 1;
        }
        // search max assigned id
        int result = Integer.MIN_VALUE;
        for (Integer assignedId : idMap.values()) {
            if (assignedId > result)
                result = assignedId;
        }
        return result + 1;
    }

    protected static String getToString(IdentityHashMap<Matcher<?>, Integer> idMap, Matcher<?> m) {
        Integer mId = idMap.get(m);
        String mRepr;
        if (mId != null) {
            mRepr = String.valueOf(mId);
        } else if (m instanceof MatcherBase) {
            mRepr = ((MatcherBase<?>) m).toString(idMap);
        } else {
            mRepr = m.toString();
        }
        return mRepr;
    }
}