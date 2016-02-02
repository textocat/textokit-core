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

package com.textocat.textokit.corpus.statistics.cpe;

import com.google.common.collect.Sets;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.CasUtil;

import java.util.Set;

public class UnitClassifier extends CasAnnotator_ImplBase {

    public static final String CLASS_FEAT_NAME = "annotatorClass";

    public static final String PARAM_CLASS_TYPE_NAMES = "classTypeNames";
    @ConfigurationParameter(name = PARAM_CLASS_TYPE_NAMES,
            mandatory = true,
            description = "Set of class type names for classifying units")
    private Set<String> classTypeNames;

    private Type unitType;
    private Feature classFeature;
    private Set<Type> classTypes = Sets.newHashSet();

    @Override
    public void typeSystemInit(TypeSystem aTypeSystem)
            throws AnalysisEngineProcessException {
        unitType = aTypeSystem.getType(UnitAnnotator.UNIT_TYPE_NAME);
        classFeature = unitType.getFeatureByBaseName(CLASS_FEAT_NAME);
        for (String classTypeName : classTypeNames) {
            classTypes.add(aTypeSystem.getType(classTypeName));
        }
    }

    @Override
    public void process(CAS aCAS) throws AnalysisEngineProcessException {
        for (AnnotationFS unit : CasUtil.select(aCAS, unitType)) {
            for (Type classType : classTypes) {
                for (AnnotationFS classAnnotation : CasUtil.select(aCAS,
                        classType)) {
                    if (isIntersect(unit, classAnnotation)) {
                        unit.setStringValue(classFeature, classType.getName());
                    }
                }
            }
        }
    }

    private static boolean isIntersect(AnnotationFS first, AnnotationFS second) {
        // See http://stackoverflow.com/a/3269471 if you don't get it
        return first.getBegin() < second.getEnd()
                && second.getBegin() < first.getEnd();
    }
}
