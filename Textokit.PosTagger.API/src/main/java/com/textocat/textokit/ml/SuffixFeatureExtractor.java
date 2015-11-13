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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;

import java.util.List;

/**
 * @author Rinat Gareev
 */
public class SuffixFeatureExtractor implements FeatureExtractor1 {

    private int maxSuffixLength;

    public SuffixFeatureExtractor(int maxSuffixLength) {
        if (maxSuffixLength <= 0) {
            throw new IllegalArgumentException();
        }
        this.maxSuffixLength = maxSuffixLength;
    }

    @Override
    public List<Feature> extract(JCas view, Annotation focusAnnotation)
            throws CleartkExtractorException {
        if (focusAnnotation instanceof Token) {
            if (!(focusAnnotation instanceof W)) {
                return ImmutableList.of();
            }
        }
        String str = focusAnnotation.getCoveredText();
        List<Feature> result = Lists.newLinkedList();
        for (int suffixLength = 1; suffixLength <= maxSuffixLength; suffixLength++) {
            String val;
            if (str.length() <= suffixLength) {
                val = str;
            } else {
                val = "*" + str.substring(str.length() - suffixLength);
            }
            result.add(new Feature(getFeatureName(suffixLength), val));
            if (str.length() <= suffixLength) {
                // suffix length increasing so there is no point to produce other features with different name
                break;
            }
        }
        return result;
    }

    private String getFeatureName(int suffixLength) {
        return Feature.createName("Suffix", String.valueOf(suffixLength));
    }
}