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


package com.textocat.textokit.commons.cpe;

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
public class CpeLauncher {

    public static void main(String[] args) throws IOException, UIMAException,
            CpeDescriptorException {
        if (args.length != 1) {
            System.err.println("Usage: <cpeDescriptorPath>");
            return;
        }
        String cpeDescPath = args[0];

        XMLInputSource cpeDescSource = new XMLInputSource(cpeDescPath);
        CpeDescription cpeDesc = UIMAFramework.getXMLParser().parseCpeDescription(cpeDescSource);

        // produce
        CollectionProcessingEngine cpe = UIMAFramework
                .produceCollectionProcessingEngine(cpeDesc);
        cpe.addStatusCallbackListener(new ReportingStatusCallbackListener(cpe));

        // run
        cpe.process();
    }
}