
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

import com.textocat.textokit.commons.consumer.XmiWriter;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.metadata.TypeSystemDescription;

import java.io.File;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * @author Rinat Gareev
 */
public class B2U {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: <typesystem-xml> <bratCorpusDir> <outputDir>");
            System.exit(1);
        }
        B2U instance = new B2U();
        instance.tsFile = new File(args[0]);
        instance.bratCorpusDir = new File(args[1]);
        instance.outputDir = new File(args[2]);
        instance.run();
    }

    private File bratCorpusDir;
    private File outputDir;
    private File tsFile;

    private B2U() {
    }

    private void run() throws Exception {
        // make TypeSystemDesc
        TypeSystemDescription tsd = TypeSystemDescriptionFactory
                .createTypeSystemDescriptionFromPath(tsFile.toURI().toString());
        // configure CollectionReader
        CollectionReaderDescription colReaderDesc = CollectionReaderFactory.createReaderDescription(
                BratCollectionReader.class, tsd,
                BratCollectionReader.PARAM_BRAT_COLLECTION_DIR, bratCorpusDir.getPath(),
                BratCollectionReader.PARAM_MAPPING_FACTORY_CLASS,
                AutoBratUimaMappingFactory.class.getName());
        // configure AE
        AnalysisEngineDescription aeDesc = createEngineDescription(XmiWriter.class,
                XmiWriter.PARAM_OUTPUTDIR, outputDir.getPath());

        SimplePipeline.runPipeline(colReaderDesc, aeDesc);
    }
}
