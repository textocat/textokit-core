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

import com.google.common.collect.Lists;
import com.textocat.textokit.commons.util.AnnotatorUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.CasUtil;

import java.util.List;

/**
 * @author Rinat Gareev
 */
public class DeleteCoveredAnnotations extends CasAnnotator_ImplBase {

    public static final String PARAM_COVERING_ANNOTATION_TYPE = "coveringAnnotationType";
    public static final String PARAM_ANNOTATION_TO_DELETE_TYPE = "annotationToDeleteType";

    @ConfigurationParameter(name = PARAM_COVERING_ANNOTATION_TYPE)
    private String coveringAnnoTypeName;
    @ConfigurationParameter(name = PARAM_ANNOTATION_TO_DELETE_TYPE)
    private String annoToDeleteTypeName;
    //
    private Type coveringAnnoType;
    private Type annoToDeleteType;

    @Override
    public void typeSystemInit(TypeSystem ts) throws AnalysisEngineProcessException {
        super.typeSystemInit(ts);
        //
        coveringAnnoType = ts.getType(coveringAnnoTypeName);
        AnnotatorUtils.annotationTypeExist(coveringAnnoTypeName, coveringAnnoType);
        //
        annoToDeleteType = ts.getType(annoToDeleteTypeName);
        AnnotatorUtils.annotationTypeExist(annoToDeleteTypeName, annoToDeleteType);
    }

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        List<AnnotationFS> anno2DeleteList = Lists.newLinkedList();
        for (AnnotationFS coveringAnno : CasUtil.select(cas, coveringAnnoType)) {
            anno2DeleteList.addAll(CasUtil.selectCovered(cas, annoToDeleteType, coveringAnno));
        }
        for (AnnotationFS anno : anno2DeleteList) {
            cas.removeFsFromIndexes(anno);
        }
    }
}
