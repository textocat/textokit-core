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

package com.textocat.textokit.commons.util;

import java.util.Objects;

/**
 * @author Rinat Gareev
 */
public class OffsetsWithValue<V> extends Offsets {
    private V value;

    public OffsetsWithValue(int begin, int end, V value) {
        super(begin, end);
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OffsetsWithValue<?> that = (OffsetsWithValue<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("begin", getBegin())
                .add("end", getEnd())
                .add("value", value)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
