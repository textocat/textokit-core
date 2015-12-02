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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.uima.cas.ArrayFS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.util.FSCollectionFactory;

import java.util.Collection;

/**
 * @author Rinat Gareev
 */
public class FSCollectionFeatureMatcher<FST extends FeatureStructure, E extends FeatureStructure>
        extends CollectionFeatureMatcherBase<FST, E> {

    public FSCollectionFeatureMatcher(Feature feature, Matcher<E> elemMatcher, boolean ignoreOrder) {
        super(feature, elemMatcher, ignoreOrder);
        Type elemType = MatchingUtils.getComponentType(feature.getRange());
        if (elemType.isPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Feature '%s' (of type '%s') range is array of primitives", feature,
                    feature.getDomain()));
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(feature).append(elemMatcher).append(ignoreOrder)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FSCollectionFeatureMatcher)) {
            return false;
        }
        FSCollectionFeatureMatcher<?, ?> that = (FSCollectionFeatureMatcher<?, ?>) obj;
        return new EqualsBuilder().append(this.feature, that.feature)
                .append(this.elemMatcher, that.elemMatcher)
                .append(this.ignoreOrder, that.ignoreOrder).isEquals();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Collection<E> getCollection(FeatureStructure anno) {
        ArrayFS fsArray = (ArrayFS) anno.getFeatureValue(feature);
        if (fsArray == null) {
            return null;
        }
        return (Collection<E>) FSCollectionFactory.create(fsArray);
    }
}