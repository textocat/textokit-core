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
public class String2StringFeatureEncoder implements FeatureEncoder<String> {

    private static final long serialVersionUID = 7109677262312932616L;

    public static final char VALUE_DELIMITER = '=';

    @Override
    public List<String> encode(Feature feature) throws CleartkEncoderException {
        StringBuilder sb = new StringBuilder();
        if (feature.getName() != null) {
            sb.append(feature.getName()).append(VALUE_DELIMITER);
        }
        sb.append(feature.getValue());
        return ImmutableList.of(sb.toString());
    }

    @Override
    public boolean encodes(Feature feature) {
        return feature.getValue() instanceof String;
    }

}
