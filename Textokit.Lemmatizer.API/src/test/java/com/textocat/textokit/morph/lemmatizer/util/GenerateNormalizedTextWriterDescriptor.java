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

package com.textocat.textokit.morph.lemmatizer.util;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * @author Rinat Gareev
 */
public class GenerateNormalizedTextWriterDescriptor {
    public static void main(String[] args) throws ResourceInitializationException, IOException, SAXException {
        AnalysisEngineDescription anDesc = createEngineDescription(NormalizedTextWriter.class);
        try (FileOutputStream out = FileUtils.openOutputStream(new File(
                "src/main/resources/" +
                        NormalizedTextWriter.class.getName().replace('.', '/') + ".xml"))) {
            anDesc.toXML(new BufferedOutputStream(out));
        }
    }
}
