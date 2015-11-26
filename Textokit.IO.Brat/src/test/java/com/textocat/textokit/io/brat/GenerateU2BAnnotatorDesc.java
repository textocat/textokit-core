
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

import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * @author Rinat Gareev
 */
public class GenerateU2BAnnotatorDesc {

    public static void main(String[] args) throws Exception {
        AnalysisEngineDescription anDesc = createEngineDescription(UIMA2BratAnnotator.class);
        FileOutputStream fout = new FileOutputStream(
                "src/main/resources/ru/kfu/itis/issst/uima/brat/UIMA2BratAnnotator.xml");
        BufferedOutputStream out = new BufferedOutputStream(fout);
        try {
            anDesc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
        System.out.println("Done");
    }

}