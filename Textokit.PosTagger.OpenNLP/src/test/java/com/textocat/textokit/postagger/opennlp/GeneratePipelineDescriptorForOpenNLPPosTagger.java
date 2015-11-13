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

package com.textocat.textokit.postagger.opennlp;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.ImmutableList;
import com.textocat.textokit.commons.util.PipelineDescriptorUtils;
import com.textocat.textokit.segmentation.SentenceSplitterAPI;
import com.textocat.textokit.tokenizer.TokenizerAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.metadata.Import;
import org.apache.uima.resource.metadata.MetaDataObject;
import org.apache.uima.resource.metadata.impl.Import_impl;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author Rinat Gareev
 */
public class GeneratePipelineDescriptorForOpenNLPPosTagger {

    public static void main(String[] args) throws UIMAException, IOException, SAXException {
        if (args.length != 1) {
            System.err.println("Provide output path!");
            System.exit(1);
        }
        File outFile = new File(args[0]);
        //
        AnalysisEngineDescription outDesc = getDescription();
        OutputStream out = FileUtils.openOutputStream(outFile);
        try {
            outDesc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public static AnalysisEngineDescription getDescription() throws UIMAException, IOException {
        Map<String, MetaDataObject> aeDescriptions = Maps.newLinkedHashMap();
        aeDescriptions.put("tokenizer", TokenizerAPI.getAEImport());
        aeDescriptions.put("sentenceSplitter", SentenceSplitterAPI.getAEImport());
        Import posTaggerDescImport = new Import_impl();
        posTaggerDescImport.setName("com.textocat.textokit.postagger.postagger-ae");
        aeDescriptions.put("pos-tagger", posTaggerDescImport);
        //
        return PipelineDescriptorUtils.createAggregateDescription(
                ImmutableList.copyOf(aeDescriptions.values()),
                ImmutableList.copyOf(aeDescriptions.keySet()));
    }

}
