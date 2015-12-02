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
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;

/**
 * @author Rinat Gareev
 */
public class PrimitiveFeatureMatcher<FST extends FeatureStructure> implements Matcher<FST> {

    private Feature feature;

    public PrimitiveFeatureMatcher(Feature feature) {
        this.feature = feature;
        if (!feature.getRange().isPrimitive()) {
            throw new IllegalArgumentException(String.format(
                    "Feature %s.%s is not primitive", feature.getDomain(), feature));
        }
        // TODO
        if (feature.getRange().getName().equals("uima.cas.Float") ||
                feature.getRange().getName().equals("uima.cas.Double")) {
            throw new UnsupportedOperationException(
                    "Floating point types matching is not implemented yet");
        }
    }

    @Override
    public boolean match(FST ref, FST cand) {
        String refValue = ref.getFeatureValueAsString(feature);
        String candValue = cand.getFeatureValueAsString(feature);
        return Objects.equal(refValue, candValue);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(feature).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrimitiveFeatureMatcher)) {
            return false;
        }
        PrimitiveFeatureMatcher<?> that = (PrimitiveFeatureMatcher<?>) obj;
        return Objects.equal(this.feature, that.feature);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("feature", feature)
                .toString();
    }

    @Override
    public void print(StringBuilder out, FST fs) {
        String featValue = fs.getFeatureValueAsString(feature);
        out.append(feature.getShortName()).append("=").append(featValue);
    }
}