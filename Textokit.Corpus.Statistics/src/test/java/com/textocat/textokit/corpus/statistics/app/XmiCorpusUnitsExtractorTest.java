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

import org.apache.uima.UIMAException;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmiCorpusUnitsExtractorTest {

    String corpusPathString = Thread.currentThread().getContextClassLoader()
            .getResource("corpus_example").getPath();

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void test() throws IOException, SAXException,
            CpeDescriptorException, ParserConfigurationException, UIMAException {
        XmiCorpusUnitsExtractor.main(new String[]{"-corpus",
                corpusPathString, "-unit",
                "com.textocat.textokit.tokenizer.fstype.W", "-class",
                "ru.kfu.itis.issst.evex.Person", "-class",
                "ru.kfu.itis.issst.evex.Organization", "-class",
                "ru.kfu.itis.issst.evex.Weapon", "-output",
                tempFolder.newFile().getPath()});
    }

}
