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

package com.textocat.textokit.ml;

import com.google.common.collect.Lists;
import org.cleartk.ml.feature.extractor.CoveredTextExtractor;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;
import org.cleartk.ml.feature.function.*;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction.Orientation;

import java.util.List;

/**
 * @author Rinat Gareev
 */
public class DefaultFeatureExtractors {

    public static List<FeatureExtractor1> currentTokenExtractors() {
        return Lists.<FeatureExtractor1>newArrayList(new FeatureFunctionExtractor(
                new CoveredTextExtractor(),
                new LowerCaseFeatureFunction(),
                new CapitalTypeFeatureFunction(),
                new NumericTypeFeatureFunction(),
                new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 3, 4, true),
                new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 2, 3, true),
                new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 1, 2, true)));
    }

    public static List<FeatureExtractor1> contextTokenExtractors() {
        return Lists.<FeatureExtractor1>newArrayList(new FeatureFunctionExtractor(
                new CoveredTextExtractor(),
                new LowerCaseFeatureFunction(),
                new NumericTypeFeatureFunction(),
                new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 3, 4, true),
                new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 2, 3, true),
                new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 1, 2, true)));
    }

    private DefaultFeatureExtractors() {
    }
}
