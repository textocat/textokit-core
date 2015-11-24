package com.textocat.textokit.io.brat;

import com.textocat.textokit.commons.cpe.XmiCollectionReader;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;

public class U2BTest {

	private static final String inputFileXMIDir = "data/news.xmi";
	public static final String U2BAggregateDesc = "desc/aggregates/u2b-test-aggregate.xml";

	@Test
	public void test() throws Exception {
		TypeSystemDescription tsd = TypeSystemDescriptionFactory
				.createTypeSystemDescription("desc.types.test-TypeSystem");

		CollectionReaderDescription colReaderDesc = CollectionReaderFactory.createReaderDescription(
				XmiCollectionReader.class, tsd,
				XmiCollectionReader.PARAM_INPUTDIR, inputFileXMIDir);

		// configure AE
		XMLInputSource aeDescInput = new XMLInputSource(U2BAggregateDesc);
		AnalysisEngineDescription aeDesc = UIMAFramework.getXMLParser()
				.parseAnalysisEngineDescription(aeDescInput);

		SimplePipeline.runPipeline(colReaderDesc, aeDesc);
	}
}