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

package com.textocat.textokit.depparser.mst;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MSTReadWriteTest {

    @Test
    public void testOnNotEmptyFile() throws UIMAException, IOException {
        File inputFile = new File("src/test/resources/mst-example.txt");
        File outputFile = new File("target/mst-example.txt");
        //
        CollectionReaderDescription colReaderDesc = MSTCollectionReader
                .createDescription(inputFile);
        AnalysisEngineDescription writerDesc = MSTWriter.createDescription(outputFile);
        SimplePipeline.runPipeline(colReaderDesc, writerDesc);
        //
        List<String> expectedLines = FileUtils.readLines(inputFile, "UTF-8");
        List<String> actualLines = FileUtils.readLines(outputFile, "UTF-8");
        // remove trailing empty line
        // actualLines.remove(actualLines.get(actualLines.size() - 1));
        //
        Assert.assertEquals(expectedLines, actualLines);
    }
}