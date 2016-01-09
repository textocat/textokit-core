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

/**
 *
 */
package com.textocat.textokit.depparser.mst;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author Rinat Gareev
 */
public class GenerateMSTParsingAnnotatorDescriptor {

    public static void main(String[] args) throws UIMAException, IOException, SAXException {
        if (args.length != 1) {
            System.err.println("Specify output dir!");
            System.exit(1);
        }
        File outputDir = new File(args[0]);
        File outputFile = new File(outputDir, "dep_parser.xml");

        AnalysisEngineDescription parserDesc = MSTParsingAnnotator.createDescription(
                new URL("file:mst-parser.model"));
        OutputStream out = FileUtils.openOutputStream(outputFile);
        try {
            parserDesc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
