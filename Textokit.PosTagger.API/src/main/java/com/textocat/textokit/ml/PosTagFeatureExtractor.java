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

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.morph.fs.Wordform;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class PosTagFeatureExtractor implements NamedFeatureExtractor1 {

    public static final String FEATURE_NAME = "PosTag";

    @Override
    public List<Feature> extract(JCas view, Annotation focusAnnotation)
            throws CleartkExtractorException {
        Word focusWord = PUtils.getWordAnno(view, focusAnnotation);
        if (focusWord == null || focusWord.getWordforms() == null) {
            return ImmutableList.of();
        }
        Collection<Wordform> wfs = FSCollectionFactory.create(focusWord.getWordforms(),
                Wordform.class);
        Set<String> tags = Sets.newHashSet();
        for (Wordform wf : wfs) {
            tags.add(wf.getPos());
        }
        return Lists.newArrayList(Collections2.transform(tags, tag2Feature));
    }

    @Override
    public String getFeatureName() {
        return FEATURE_NAME;
    }

    private static final Function<String, Feature> tag2Feature = new Function<String, Feature>() {
        @Override
        public Feature apply(String tag) {
            return new Feature(FEATURE_NAME, tag);
        }
    };
}
