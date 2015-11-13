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

import com.google.common.collect.ImmutableList;
import org.cleartk.ml.Feature;
import org.cleartk.ml.encoder.CleartkEncoderException;
import org.cleartk.ml.encoder.features.FeatureEncoder;

import java.util.List;

/**
 * @author Rinat Gareev
 */
public class Boolean2StringFeatureEncoder implements FeatureEncoder<String> {

    private static final long serialVersionUID = -2489930447901347937L;

    @Override
    public List<String> encode(Feature feature) throws CleartkEncoderException {
        if ((Boolean) feature.getValue()) {
            return ImmutableList.of(feature.getName());
        } else {
            return ImmutableList.of();
        }
    }

    @Override
    public boolean encodes(Feature feature) {
        return feature.getValue() instanceof Boolean;
    }

}
