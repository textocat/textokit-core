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


package com.textocat.textokit.commons.util;

import com.textocat.textokit.commons.cpe.ReportingStatusCallbackListener;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.metadata.CpeDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.util.XMLInputSource;

import java.io.IOException;

/**
 * @author Rinat Gareev
 */
// TODO rewrite using CpeLauncher
public class TxtToXmiLauncher {

    public static void main(String[] args) throws IOException, UIMAException,
            CpeDescriptorException {
        if (args.length != 2) {
            System.err.println("Usage: <txtInputDir> <xmiOutputDir>");
            return;
        }
        String cpeDescPath = "desc/cpe/cpe-txt-to-xmi.xml";
        String inputDirPath = args[0];
        String outputDirPath = args[1];

        XMLInputSource cpeDescSource = new XMLInputSource(cpeDescPath);
        CpeDescription cpeDesc = UIMAFramework.getXMLParser().parseCpeDescription(cpeDescSource);

        // configure reader
        cpeDesc.getAllCollectionCollectionReaders()[0]
                .getConfigurationParameterSettings().setParameterValue("DirectoryPath",
                inputDirPath);

        // configure writer
        cpeDesc.getCpeCasProcessors().getAllCpeCasProcessors()[0]
                .getConfigurationParameterSettings()
                .setParameterValue("XmiOutputDir", outputDirPath);

        // produce
        CollectionProcessingEngine cpe = UIMAFramework
                .produceCollectionProcessingEngine(cpeDesc);
        cpe.addStatusCallbackListener(new ReportingStatusCallbackListener(cpe));

        // run
        cpe.process();
    }
}