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

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * @author Rinat Gareev
 */
public class FSFeatureMatcher<S extends FeatureStructure, E extends FeatureStructure>
        extends MatcherBase<S> {

    private Feature feature;
    private Matcher<E> valueMatcher;

    public FSFeatureMatcher(Feature feature, Matcher<E> valueMatcher) {
        this.feature = feature;
        this.valueMatcher = valueMatcher;
        if (MatchingUtils.isCollectionType(feature.getRange())) {
            throw new IllegalArgumentException(String.format(
                    "Feature '%s' (of type '%s') range is array", feature, feature.getDomain()));
        }
    }

    public Feature getFeature() {
        return feature;
    }

    @Override
    public boolean match(S ref, S cand) {
        E refValue = getValue(ref);
        E candValue = getValue(cand);
        if (refValue == null) {
            return candValue == null;
        }
        if (candValue == null) {
            return false;
        }
        return valueMatcher.match(refValue, candValue);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(feature).append(valueMatcher).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FSFeatureMatcher)) {
            return false;
        }
        FSFeatureMatcher<?, ?> that = (FSFeatureMatcher<?, ?>) obj;
        return new EqualsBuilder().append(this.feature, that.feature)
                .append(this.valueMatcher, that.valueMatcher)
                .isEquals();
    }

    @Override
    protected String toString(IdentityHashMap<Matcher<?>, Integer> idMap) {
        idMap.put(this, getNextId(idMap));
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("feature", feature)
                .append("valueMatcher", getToString(idMap, valueMatcher)).toString();
    }

    @Override
    protected Collection<Matcher<?>> getSubMatchers() {
        List<Matcher<?>> result = Lists.newLinkedList();
        result.add(valueMatcher);
        return result;
    }

    @Override
    public void print(StringBuilder out, S value) {
        out.append(feature.getShortName()).append("=");
        E featValue = getValue(value);
        if (featValue == null) {
            out.append((Object) null);
        } else {
            valueMatcher.print(out, featValue);
        }
    }

    @SuppressWarnings("unchecked")
    private E getValue(FeatureStructure fs) {
        E featureValue = (E) fs.getFeatureValue(feature);
        return featureValue;
    }
}