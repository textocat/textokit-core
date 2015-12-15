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
import org.apache.uima.resource.ResourceInitializationException;

/**
 * The default implementation of {@link ChunkAnnotationAdapter} that completely ignores chunk metadata
 * and create annotation of a type given by the configuration.
 *
 * @author Rinat Gareev
 */
public class DefaultChunkAnnotationAdapter implements ChunkAnnotationAdapter<Object>, Initializable {

    public static final String PARAM_RESULT_ANNOTATION_TYPE = "resultAnnotationType";

    @ConfigurationParameter(name = PARAM_RESULT_ANNOTATION_TYPE)
    private Class<? extends AnnotationFS> resultAnnoClass;
    // derived
    private Type resultAnnoType;
    private Feature firstTokenFeature;

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(this, ctx);
    }

    @Override
    public void typeSystemInit(TypeSystem ts) {
        resultAnnoType = FSTypeUtils.getType(ts, resultAnnoClass.getName(), true);
        // TODO describe this in the class javadoc
        firstTokenFeature = FSTypeUtils.getFeature(resultAnnoType, "firstToken", false);
    }

    @Override
    public void makeAnnotation(AnnotationFS firstToken, AnnotationFS lastToken, Object metadata) {
        Preconditions.checkArgument(firstToken != null, "firstToken == null");
        CAS cas = firstToken.getCAS();
        AnnotationFS resAnno = cas.createAnnotation(resultAnnoType, firstToken.getBegin(), lastToken.getEnd());
        if (firstTokenFeature != null) {
            resAnno.setFeatureValue(firstTokenFeature, firstToken);
        }
        cas.addFsToIndexes(resAnno);
    }
}
