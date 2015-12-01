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

package org.nlplab.brat.configuration;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class BratAttributeType extends BratType {
    private Set<String> values;

    public BratAttributeType(String name, Set<String> values) {
        super(name);
        if (values == null) {
            values = Sets.newHashSet();
        }
        this.values = ImmutableSet.copyOf(values);
    }

    public BratAttributeType(String name, String... values) {
        super(name);
        this.values = ImmutableSet.copyOf(values);
    }

    public Set<String> getValues() {
        return values;
    }

    public boolean isBinary() {
        return values.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BratAttributeType that = (BratAttributeType) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("name", name)
                .add("values", values)
                .toString();
    }
}
