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


package com.textocat.textokit.commons.cas;

import com.google.common.base.Objects;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FeatureStructure;

/**
 * @author Rinat Gareev
 */
class IdentityConstraint implements FSMatchConstraint {

    private static final long serialVersionUID = -2827846978417612056L;
    private FeatureStructure fs;

    private IdentityConstraint(FeatureStructure fs) {
        this.fs = fs;
    }

    public static IdentityConstraint of(FeatureStructure fs) {
        return new IdentityConstraint(fs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(FeatureStructure fs) {
        return Objects.equal(this.fs, fs);
    }

}