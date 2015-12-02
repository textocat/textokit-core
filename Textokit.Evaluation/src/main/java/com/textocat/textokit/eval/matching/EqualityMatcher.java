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

import com.google.common.base.Objects;

/**
 * @author Rinat Gareev
 */
public class EqualityMatcher<T> implements Matcher<T> {

    private static final EqualityMatcher<?> INSTANCE = new EqualityMatcher<Object>();

    @SuppressWarnings("unchecked")
    public static final <T> EqualityMatcher<T> getInstance() {
        return (EqualityMatcher<T>) INSTANCE;
    }

    private EqualityMatcher() {
    }

    @Override
    public boolean match(T ref, T cand) {
        return Objects.equal(ref, cand);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void print(StringBuilder out, T value) {
        out.append(value);
    }
}