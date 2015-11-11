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


package com.textocat.textokit.commons.annotator;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.textocat.textokit.commons.cas.AnnotationUtils;
import com.textocat.textokit.commons.util.AnnotatorUtils;
import com.textocat.textokit.commons.util.DocumentUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import com.textocat.textokit.commons.cas.OverlapIndex;

import java.util.Set;

import static com.textocat.textokit.commons.util.DocumentUtils.getDocumentUri;

/**
 * @author Rinat Gareev
 */
public class NestedAnnotationRemover extends CasAnnotator_ImplBase {

    public static final String PARAM_TARGET_TYPE = "targetType";
    @ConfigurationParameter(name = PARAM_TARGET_TYPE, mandatory = true)
    private String targetTypeName;
    // derived
    private Type targetType;
    // collection-scope state fields
    private int annotationsInspected;
    private int annotationsRemoved;

    public static AnalysisEngineDescription createDescription(String targetTypeName)
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(NestedAnnotationRemover.class,
                PARAM_TARGET_TYPE, targetTypeName);
    }

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
    }

    @Override
    public void typeSystemInit(TypeSystem ts) throws AnalysisEngineProcessException {
        super.typeSystemInit(ts);
        targetType = ts.getType(targetTypeName);
        AnnotatorUtils.annotationTypeExist(targetTypeName, targetType);
    }

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // collect annotations to remove
        Set<AnnotationFS> annotations2Remove = Sets.newHashSet();
        AnnotationIndex<AnnotationFS> targetAnnoIdx = cas.getAnnotationIndex(targetType);
        annotationsInspected += targetAnnoIdx.size();
        OverlapIndex<AnnotationFS> overlapIdx = AnnotationUtils.createOverlapIndex(
                targetAnnoIdx.iterator());
        for (AnnotationFS anno : targetAnnoIdx) {
            // longer annotations come first in the default index
            if (annotations2Remove.contains(anno)) {
                continue;
            }
            Set<AnnotationFS> overlapping = overlapIdx.getOverlapping(
                    anno.getBegin(), anno.getEnd());
            // remote itself from overlapping
            overlapping.remove(anno);
            if (!overlapping.isEmpty()) {
                annotations2Remove.addAll(overlapping);
                if (getLogger().isDebugEnabled()) {
                    getLogger()
                            .debug(
                                    String.format(
                                            "The following annotations overlap with %s and will be deleted:\n%s",
                                            AnnotationUtils.toPrettyString(anno),
                                            Collections2.transform(overlapping,
                                                    AnnotationUtils.coveredTextFunction())));
                }
            }
        }
        for (AnnotationFS anno : annotations2Remove) {
            cas.removeFsFromIndexes(anno);
        }
        annotationsRemoved += annotations2Remove.size();
        if (getLogger().isTraceEnabled()) {
            getLogger().trace(String.format(
                    "%s nested %ss were removed from %s",
                    annotationsRemoved, targetType.getShortName(), DocumentUtils.getDocumentUri(cas)));
        }
    }

    @Override
    public void collectionProcessComplete() throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        getLogger().info(String.format(
                "Report for %s:\n"
                        + "inspected: %s\n"
                        + "removed: %s",
                targetType.getName(), annotationsInspected, annotationsRemoved));
        annotationsInspected = 0;
        annotationsRemoved = 0;
    }

}
