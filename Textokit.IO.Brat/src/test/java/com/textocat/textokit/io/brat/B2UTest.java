package com.textocat.textokit.io.brat;

import com.textocat.textokit.commons.consumer.XmiFileWriter;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.junit.Test;

import java.io.File;

public class B2UTest {

    private static final String inputBratDir = "data/news.brat";

    @Test
    public void testReverse() throws Exception {
        TypeSystemDescription tsd = TypeSystemDescriptionFactory
                .createTypeSystemDescription("desc.types.test-TypeSystem");

        CollectionReaderDescription colReaderDesc = CollectionReaderFactory.createReaderDescription(
                BratCollectionReader.class, tsd,
                BratCollectionReader.PARAM_BRAT_COLLECTION_DIR, inputBratDir,
                BratCollectionReader.PARAM_MAPPING_FACTORY_CLASS,
                ReverseBratUimaMappingFactory.class.getName(),
                ReverseBratUimaMappingFactory.PARAM_U2B_DESC_PATH, U2BTest.U2BAggregateDesc);

        // configure AE
        AnalysisEngineDescription aeDesc = XmiFileWriter.createDescription(
                new File("target/test-brat2uima-output"));

        SimplePipeline.runPipeline(colReaderDesc, aeDesc);
    }

    @Test
    public void testAuto() throws Exception {
        TypeSystemDescription tsd = TypeSystemDescriptionFactory
                .createTypeSystemDescription("desc.types.brat-news-tutorial-TypeSystem");

        CollectionReaderDescription colReaderDesc = CollectionReaderFactory.createReaderDescription(
                BratCollectionReader.class, tsd,
                BratCollectionReader.PARAM_BRAT_COLLECTION_DIR, "data/brat-news-tutorial",
                BratCollectionReader.PARAM_MAPPING_FACTORY_CLASS,
                AutoBratUimaMappingFactory.class.getName(),
                AutoBratUimaMappingFactory.PARAM_NAMESPACES_TO_SCAN, "ace");

        // configure AE
        AnalysisEngineDescription aeDesc = XmiFileWriter.createDescription(
                new File("target/brat-news-tutorial.xmi"));

        SimplePipeline.runPipeline(colReaderDesc, aeDesc);
    }
}