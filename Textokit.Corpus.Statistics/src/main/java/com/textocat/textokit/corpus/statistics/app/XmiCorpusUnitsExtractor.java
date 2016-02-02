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

package com.textocat.textokit.corpus.statistics.app;

import com.beust.jcommander.JCommander;
import com.textocat.textokit.corpus.statistics.cpe.*;
import com.textocat.textokit.corpus.statistics.dao.corpus.XmiFileTreeCorpusDAO;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmiCorpusUnitsExtractor {

    private ExternalResourceDescription daoDesc;
    private TypeSystemDescription tsd;
    private CollectionReaderDescription reader;
    private AnalysisEngineDescription tokenizerSentenceSplitter;
    private AnalysisEngineDescription unitAnnotator;
    private AnalysisEngineDescription unitClassifier;
    private AnalysisEngineDescription unitsDAOWriter;
    private AnalysisEngineDescription aggregate;

    public static void main(String[] args)
            throws IOException, SAXException,
            CpeDescriptorException, ParserConfigurationException, UIMAException {
        XmiCorpusUnitsExtractor extractor = new XmiCorpusUnitsExtractor(args);
        extractor.process();
    }

    XmiCorpusUnitsExtractor(String[] args)
            throws IOException, SAXException,
            CpeDescriptorException, ParserConfigurationException, UIMAException {
        XmiCorpusUnitsExtractorParams extractorParams = new XmiCorpusUnitsExtractorParams();
        new JCommander(extractorParams, args);

        daoDesc = ExternalResourceFactory.createExternalResourceDescription(
                XmiFileTreeCorpusDAOResource.class, extractorParams.corpus);
        tsd = XmiFileTreeCorpusDAO.getTypeSystem(extractorParams.corpus);
        reader = CollectionReaderFactory.createReaderDescription(
                CorpusDAOCollectionReader.class, tsd,
                CorpusDAOCollectionReader.CORPUS_DAO_KEY, daoDesc);

        tokenizerSentenceSplitter = AnalysisEngineFactory
                .createEngineDescription(Unitizer
                        .createTokenizerSentenceSplitterAED());

        unitAnnotator = AnalysisEngineFactory.createEngineDescription(
                UnitAnnotator.class, UnitAnnotator.PARAM_UNIT_TYPE_NAMES,
                extractorParams.units);
        unitClassifier = AnalysisEngineFactory.createEngineDescription(
                UnitClassifier.class, UnitClassifier.PARAM_CLASS_TYPE_NAMES,
                extractorParams.classes);

        unitsDAOWriter = AnalysisEngineFactory.createEngineDescription(
                UnitsDAOWriter.class, UnitsDAOWriter.UNITS_TSV_PATH,
                extractorParams.output);
        aggregate = AnalysisEngineFactory.createEngineDescription(
                tokenizerSentenceSplitter, unitAnnotator, unitClassifier,
                unitsDAOWriter);
    }

    public void process() throws UIMAException, IOException {
        SimplePipeline.runPipeline(reader, aggregate);
    }
}
