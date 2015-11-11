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

import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FeatureStructure;

/**
 * @author Rinat Gareev
 */
class NotConstraint implements FSMatchConstraint {

    private static final long serialVersionUID = -2673253566146696649L;
    private FSMatchConstraint argConstraint;

    private NotConstraint(FSMatchConstraint argConstraint) {
        this.argConstraint = argConstraint;
    }

    public static NotConstraint of(FSMatchConstraint argConstraint) {
        return new NotConstraint(argConstraint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(FeatureStructure fs) {
        return !argConstraint.match(fs);
    }
}