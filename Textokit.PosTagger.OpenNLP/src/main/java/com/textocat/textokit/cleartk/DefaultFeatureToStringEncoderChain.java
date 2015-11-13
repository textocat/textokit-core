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

package com.textocat.textokit.cleartk;

import org.cleartk.ml.encoder.features.FeatureEncoderChain;

/**
 * @author Rinat Gareev
 */
public class DefaultFeatureToStringEncoderChain extends FeatureEncoderChain<String> {

    private static final long serialVersionUID = 5915214180458567772L;

    public DefaultFeatureToStringEncoderChain() {
        addEncoder(new Boolean2StringFeatureEncoder());
        addEncoder(new String2StringFeatureEncoder());
    }

}
