/**
 *
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
