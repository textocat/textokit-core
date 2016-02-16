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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.textocat.textokit.commons.util.AnnotatorUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.initializable.Initializable;
import org.apache.uima.fit.internal.ExtendedLogger;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.Map;

/**
 * @author Rinat Gareev
 */
public class TypedChunkAnnotationAdapter implements ChunkAnnotationAdapter<String>, Initializable {

    public static final String PARAM_TARGET_NAMESPACE = "targetNamespace";
    public static final String PARAM_TAGS = "tags";
    public static final String PARAM_ANNOTATION_TYPES = "annotationTypes";

    @ConfigurationParameter(name = PARAM_TARGET_NAMESPACE, mandatory = false)
    private String baseNamespace;
    @ConfigurationParameter(name = PARAM_TAGS)
    private String[] pTags;
    @ConfigurationParameter(name = PARAM_ANNOTATION_TYPES)
    private String[] pTypes;
    //
    private Map<String, Type> tag2TypeMap;
    private ExtendedLogger logger;

    @Override
    public void typeSystemInit(TypeSystem ts) {
        tag2TypeMap = Maps.newHashMap();
        for (int i = 0; i < pTags.length; i++) {
            String tag = pTags[i];
            String relTypeName = pTypes[i];
            String typeName;
            if (baseNamespace != null) {
                typeName = baseNamespace + "." + relTypeName;
            } else {
                typeName = relTypeName;
            }
            Type type = ts.getType(typeName);
            try {
                AnnotatorUtils.annotationTypeExist(typeName, type);
            } catch (AnalysisEngineProcessException e) {
                throw new IllegalStateException(e);
            }
            tag2TypeMap.put(tag, type);
        }
        tag2TypeMap = ImmutableMap.copyOf(tag2TypeMap);
    }

    @Override
    public void makeAnnotation(AnnotationFS firstToken, AnnotationFS lastToken, String tag) {
        Preconditions.checkArgument(firstToken != null, "firstToken == null");
        CAS cas = firstToken.getCAS();
        Type targetType = tag2TypeMap.get(tag);
        if (targetType == null) {
            logger.warn(String.format("Dictionary tag '%s' is not mapped to any annotation type", tag));
        } else {
            AnnotationFS anno = cas.createAnnotation(targetType, firstToken.getBegin(), lastToken.getEnd());
            Feature firstTokenFeature = targetType.getFeatureByBaseName("firstToken");
            // TODO doc
            if (firstTokenFeature != null) {
                anno.setFeatureValue(firstTokenFeature, firstToken);
            }
            cas.addFsToIndexes(anno);
        }
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(this, context);
        logger = new ExtendedLogger(context);
        //
        if (pTags.length != pTypes.length) {
            throw new IllegalStateException(
                    "parameter 'tags' should contain keys for a map with values from parameter 'annotationTypes'");
        }
    }
}
