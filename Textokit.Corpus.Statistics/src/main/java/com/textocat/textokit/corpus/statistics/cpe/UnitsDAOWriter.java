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

import com.textocat.textokit.corpus.statistics.dao.units.UnitsDAO;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.*;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.resource.ResourceInitializationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@OperationalProperties(multipleDeploymentAllowed = false)
public class UnitsDAOWriter extends CasAnnotator_ImplBase {

    public final static String UNITS_TSV_PATH = "UnitsTSVPath";
    @ConfigurationParameter(name = UNITS_TSV_PATH,
            defaultValue = "units.tsv",
            mandatory = false,
            description = "Path to file to write units")
    private File unitsTSV;

    final static String UNITS_DAO_IMPLEMENTATION_CLASS_NAME = "UnitsDAOImplementationClassName";
    @ConfigurationParameter(name = UNITS_DAO_IMPLEMENTATION_CLASS_NAME,
            defaultValue = "com.textocat.textokit.corpus.statistics.dao.units.InMemoryUnitsDAO",
            mandatory = false,
            description = "Class name of UnitsDAO implementation")
    private String unitsDAOImplementationClassName;
    private UnitsDAO unitsDAO;

    private Type unitType;
    private Feature classFeature;

    private Type sourceDocumentInformationType;
    private Feature uriFeature;
    private Feature annotatorIdFeature;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            unitsDAO = (UnitsDAO) Class
                    .forName(unitsDAOImplementationClassName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void typeSystemInit(TypeSystem aTypeSystem)
            throws AnalysisEngineProcessException {
        unitType = aTypeSystem.getType(UnitAnnotator.UNIT_TYPE_NAME);
        classFeature = unitType
                .getFeatureByBaseName(UnitClassifier.CLASS_FEAT_NAME);

        sourceDocumentInformationType = aTypeSystem
                .getType(CorpusDAOCollectionReader.SOURCE_DOCUMENT_INFORMATION_TYPE_NAME);
        uriFeature = sourceDocumentInformationType
                .getFeatureByBaseName(CorpusDAOCollectionReader.URI_FEAT_NAME);
        annotatorIdFeature = sourceDocumentInformationType
                .getFeatureByBaseName(CorpusDAOCollectionReader.ANNOTATOR_ID_FEAT_NAME);

    }

    @Override
    public void process(CAS aCAS) throws AnalysisEngineProcessException {
        for (AnnotationFS unit : CasUtil.select(aCAS, unitType)) {
            FeatureStructure sourceDocumentInformation = CasUtil.selectSingle(
                    aCAS, sourceDocumentInformationType);
            try {
                unitsDAO.addUnitItem(
                        new URI(sourceDocumentInformation
                                .getStringValue(uriFeature)), unit.getBegin(),
                        unit.getEnd(), sourceDocumentInformation
                                .getStringValue(annotatorIdFeature), unit
                                .getStringValue(classFeature));
            } catch (CASRuntimeException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        BufferedWriter outputStream = null;
        try {
            outputStream = new BufferedWriter(new FileWriter(unitsTSV));
            unitsDAO.toTSV(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

}
