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

package com.textocat.textokit.postagger.opennlp;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.textocat.textokit.commons.util.ConfigPropertiesUtils;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.cleartk.ml.feature.extractor.CleartkExtractor;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Context;
import org.cleartk.ml.feature.extractor.CombinedExtractor1;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;

import java.util.List;
import java.util.Properties;

/**
 * @author Rinat Gareev
 */
// TODO eliminate this inheritance
public class DefaultFeatureExtractors extends FeatureExtractorsBasedContextGenerator {

    public static final String PROP_DICTIONARY_VERSION = "dictionary.version";

    public static DefaultFeatureExtractors from(Properties props, MorphDictionary morphDict) {
        int prevTagsInHistory = ConfigPropertiesUtils.getIntProperty(props, "prevTags");
        int leftContextSize = ConfigPropertiesUtils.getIntProperty(props, "leftContext");
        int rightContextSize = ConfigPropertiesUtils.getIntProperty(props, "rightContext");
        if (morphDict != null) {
            String morphDictVersion = ConfigPropertiesUtils.getStringProperty(props,
                    PROP_DICTIONARY_VERSION);
            if (!Objects.equal(morphDictVersion, morphDict.getVersion())) {
                throw new IllegalStateException(String.format(
                        "Dictionary versions do not match:\n"
                                + "The feature extractors have %s\n"
                                + "The supplied dictionary has %s",
                        morphDictVersion, morphDict.getVersion()));
            }
        }
        String targetGramCategoriesStr = ConfigPropertiesUtils.getStringProperty(
                props, "targetGramCategories");
        Iterable<String> targetGramCategories = Splitter.on(',').trimResults()
                .split(targetGramCategoriesStr);
        return new DefaultFeatureExtractors(prevTagsInHistory, leftContextSize, rightContextSize,
                targetGramCategories, morphDict);
    }

    public static void to(DefaultFeatureExtractors obj, Properties props) {
        props.setProperty("prevTags", String.valueOf(obj.getPrevTagsInHistory()));
        props.setProperty("leftContext", String.valueOf(obj.leftContextSize));
        props.setProperty("rightContext", String.valueOf(obj.rightContextSize));
        props.setProperty("targetGramCategories",
                Joiner.on(',').join(obj.getTargetGramCategories()));
        if (obj.getMorphDict() != null) {
            props.setProperty(PROP_DICTIONARY_VERSION, obj.getMorphDict().getVersion());
        }
    }

    private int leftContextSize;
    private int rightContextSize;

    public DefaultFeatureExtractors(int prevTagsInHistory,
                                    int leftContextSize, int rightContextSize,
                                    Iterable<String> targetGramCategories,
                                    MorphDictionary morphDict) {
        super(prevTagsInHistory, defaultExtractors(leftContextSize, rightContextSize),
                targetGramCategories, morphDict);
        this.leftContextSize = leftContextSize;
        this.rightContextSize = rightContextSize;
    }

    public int getLeftContextSize() {
        return leftContextSize;
    }

    public int getRightContextSize() {
        return rightContextSize;
    }

    public static List<FeatureExtractor1> defaultExtractors(int leftContextSize,
                                                            int rightContextSize) {
        List<FeatureExtractor1> feList = Lists.newLinkedList();
        feList.addAll(com.textocat.textokit.ml.DefaultFeatureExtractors.currentTokenExtractors());

        List<FeatureExtractor1> ctxTokenFeatureExtractors = com.textocat.textokit.ml.DefaultFeatureExtractors.contextTokenExtractors();

        if (leftContextSize < 0 || rightContextSize < 0) {
            throw new IllegalStateException("context size < 0");
        }
        if (leftContextSize == 0 && rightContextSize == 0) {
            throw new IllegalStateException("left & right context sizes == 0");
        }
        List<Context> contexts = Lists.newArrayList();
        if (leftContextSize > 0) {
            contexts.add(new CleartkExtractor.Preceding(leftContextSize));
        }
        if (rightContextSize > 0) {
            contexts.add(new CleartkExtractor.Following(rightContextSize));
        }
        feList.add(new CleartkExtractor(Token.class,
                new CombinedExtractor1(ctxTokenFeatureExtractors),
                contexts.toArray(new Context[contexts.size()])));
        return feList;
    }
}
