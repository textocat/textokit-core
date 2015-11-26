
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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public class EventRole {
    public static enum Cardinality {
        ONE(false), OPTIONAL(false), ARRAY(true), NON_EMPTY_ARRAY(true);

        private boolean allowsMultipleValues;

        private Cardinality(boolean allowsMultipleValues) {
            this.allowsMultipleValues = allowsMultipleValues;
        }

        public boolean allowsMultipleValues() {
            return allowsMultipleValues;
        }
    }

    private String role;
    private Set<BratType> rangeTypes;
    private Cardinality cardinality;

    public EventRole(String role, Iterable<BratType> rangeTypes, Cardinality cardinality) {
        this.role = role;
        this.rangeTypes = ImmutableSet.copyOf(rangeTypes);
        if (this.rangeTypes.isEmpty()) {
            throw new IllegalArgumentException("Empty rangeTypes");
        }
        this.cardinality = cardinality;
        if (role == null || rangeTypes == null || cardinality == null) {
            throw new NullPointerException();
        }
    }

    public EventRole(String role, BratType range, Cardinality cardinality) {
        this(role, ImmutableSet.of(range), cardinality);
    }

    public String getRole() {
        return role;
    }

    public Set<BratType> getRangeTypes() {
        return rangeTypes;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EventRole)) {
            return false;
        }
        EventRole that = (EventRole) obj;
        return new EqualsBuilder().append(this.role, that.role)
                .append(this.rangeTypes, that.rangeTypes)
                .append(this.cardinality, that.cardinality).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_FIELD_NAMES_STYLE)
                .append(role).append(rangeTypes).append(cardinality).toString();
    }
}