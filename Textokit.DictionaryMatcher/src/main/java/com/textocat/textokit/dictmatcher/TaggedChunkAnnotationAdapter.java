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

package com.textocat.textokit.dictmatcher;

import com.google.common.base.Preconditions;
import com.textocat.textokit.commons.cas.FSTypeUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.initializable.Initializable;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * @author Rinat Gareev
 */
public class TaggedChunkAnnotationAdapter implements ChunkAnnotationAdapter<String>, Initializable {

    public static final String PARAM_RESULT_ANNOTATION_TYPE = "resultAnnotationType";
    public static final String PARAM_TAG_FEATURE = "tagFeature";

    @ConfigurationParameter(name = PARAM_RESULT_ANNOTATION_TYPE)
    private Class<? extends Annotation> resultAnnotationClass;
    @ConfigurationParameter(name = PARAM_TAG_FEATURE, mandatory = true)
    private String tagFeatureName;
    //
    private Type resultAnnotationType;
    private Feature tagFeature;
    private Feature firstTokenFeature;

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(this, ctx);
    }

    @Override
    public void typeSystemInit(TypeSystem ts) {
        resultAnnotationType = FSTypeUtils.getType(ts, resultAnnotationClass.getName(), true);
        tagFeature = FSTypeUtils.getFeature(resultAnnotationType, tagFeatureName, true);
        if (!ts.subsumes(ts.getType(CAS.TYPE_NAME_STRING), tagFeature.getRange())) {
            throw new IllegalStateException(String.format(
                    "Feature %s can not hold dictionary tags as its range is %s",
                    tagFeature, tagFeature.getRange()));
        }
        // TODO describe this in the class javadoc
        firstTokenFeature = FSTypeUtils.getFeature(resultAnnotationType, "firstToken", false);
    }

    @Override
    public void makeAnnotation(AnnotationFS firstToken, AnnotationFS lastToken, String tag) {
        Preconditions.checkArgument(firstToken != null, "firstToken == null");
        CAS cas = firstToken.getCAS();
        AnnotationFS anno = cas.createAnnotation(resultAnnotationType, firstToken.getBegin(), lastToken.getEnd());
        anno.setStringValue(tagFeature, tag);
        if (firstTokenFeature != null) {
            anno.setFeatureValue(firstTokenFeature, firstToken);
        }
        cas.addFsToIndexes(anno);
    }
}
