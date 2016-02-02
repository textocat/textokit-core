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
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.CasUtil;

import java.util.Set;

public class UnitAnnotator extends CasAnnotator_ImplBase {

    public static final String UNIT_TYPE_NAME = "com.textocat.textokit.corpus.statistics.type.Unit";

    public static final String PARAM_UNIT_TYPE_NAMES = "unitTypeNames";
    @ConfigurationParameter(name = PARAM_UNIT_TYPE_NAMES,
            mandatory = true,
            description = "Set of unit type names, for which unit annotation will be created")
    private Set<String> unitTypeNames;

    private Type unitType;
    private Set<Type> unitTypes = Sets.newHashSet();

    @Override
    public void typeSystemInit(TypeSystem aTypeSystem)
            throws AnalysisEngineProcessException {
        unitType = aTypeSystem.getType(UNIT_TYPE_NAME);
        for (String unitTypeName : unitTypeNames) {
            unitTypes.add(aTypeSystem.getType(unitTypeName));
        }
    }

    @Override
    public void process(CAS aCAS) throws AnalysisEngineProcessException {
        for (Type unitSourceType : unitTypes) {
            for (AnnotationFS unitSource : CasUtil.select(aCAS, unitSourceType)) {
                AnnotationFS unit = aCAS.createAnnotation(unitType,
                        unitSource.getBegin(), unitSource.getEnd());
                aCAS.addFsToIndexes(unit);
            }
        }
    }
}
