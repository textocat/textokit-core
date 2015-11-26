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

package com.textocat.textokit.io.brat;

import com.textocat.textokit.commons.cpe.XmiCollectionReader;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;

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