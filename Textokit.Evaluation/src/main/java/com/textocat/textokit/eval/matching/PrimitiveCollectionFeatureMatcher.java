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

import com.textocat.textokit.commons.cas.FSUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.StringArrayFS;
import org.apache.uima.cas.Type;

import java.util.Collection;

/**
 * @author Rinat Gareev
 */
public abstract class PrimitiveCollectionFeatureMatcher<FST extends FeatureStructure, E>
        extends CollectionFeatureMatcherBase<FST, E> {

    public static <FST extends FeatureStructure> Matcher<FST> forFeature(
            Feature feat, boolean ignoreOrder) {
        Type compType = MatchingUtils.getComponentType(feat.getRange());
        if (!compType.isPrimitive()) {
            throw new IllegalStateException(String.format(
                    "Component type %s of feature %s is not primitive type",
                    compType, feat));
        }
        if ("uima.cas.String".equals(compType.getName())) {
            return forStringCollection(feat, ignoreOrder);
        } else {
            // TODO LOW PRIORITY
            throw new UnsupportedOperationException(String.format(
                    "PrimitiveCollectionFeatureMatcher for %s is not implemented yet", compType));
        }
    }

    private static <FST extends FeatureStructure> PrimitiveCollectionFeatureMatcher<FST, String> forStringCollection(
            final Feature feat, boolean ignoreOrder) {
        return new PrimitiveCollectionFeatureMatcher<FST, String>(feat, ignoreOrder) {
            @Override
            protected Collection<String> getCollection(FST srcFS) {
                return FSUtils.toList((StringArrayFS) srcFS.getFeatureValue(feat));
            }
        };
    }

    private PrimitiveCollectionFeatureMatcher(Feature feature,
                                              boolean ignoreOrder) {
        super(feature, EqualityMatcher.<E>getInstance(), ignoreOrder);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ignoreOrder).append(elemMatcher).append(feature)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrimitiveCollectionFeatureMatcher)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        PrimitiveCollectionFeatureMatcher<?, ?> that = (PrimitiveCollectionFeatureMatcher<?, ?>) obj;
        return new EqualsBuilder().append(this.ignoreOrder, that.ignoreOrder)
                .append(this.feature, that.feature)
                .append(this.elemMatcher, that.elemMatcher).isEquals();
    }
}