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

package com.textocat.textokit.postagger;

import com.google.common.collect.Maps;
import com.textocat.textokit.commons.util.PipelineDescriptorUtils;
import com.textocat.textokit.morph.commons.GramModelBasedTagMapper;
import com.textocat.textokit.morph.commons.TagAssembler;
import com.textocat.textokit.morph.dictionary.MorphologyAnnotator;
import com.textocat.textokit.segmentation.SentenceSplitterAPI;
import com.textocat.textokit.tokenizer.TokenizerAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.metadata.MetaDataObject;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static com.textocat.textokit.morph.dictionary.MorphDictionaryAPIFactory.getMorphDictionaryAPI;
import static org.apache.uima.fit.factory.ExternalResourceFactory.bindExternalResource;

/**
 * @author Rinat Gareev
 */
public class GenerateAggregateDescriptorForMorphAnnotator {

    public static void main(String[] args) throws UIMAException, IOException, SAXException {
        if (args.length != 1) {
            System.err.println("Usage: <output-path>");
            System.exit(1);
        }
        String outputPath = args[0];
        // NOTE! A file URL for generated SerializedDictionaryResource description assumes
        // that the required dictionary file is within one of UIMA datapath folders.
        // So users of the generated aggregate descriptor should setup 'uima.datapath' properly .
        ExternalResourceDescription morphDictDesc = getMorphDictionaryAPI()
                .getResourceDescriptionWithPredictorEnabled();

        Map<String, MetaDataObject> aeDescriptions = Maps.newLinkedHashMap();
        aeDescriptions.put("tokenizer", TokenizerAPI.getAEImport());
        //
        aeDescriptions.put("sentence-splitter", SentenceSplitterAPI.getAEImport());
        //
        aeDescriptions.put("morph-analyzer", MorphologyAnnotator.createDescription(
                DefaultAnnotationAdapter.class, PosTaggerAPI.getTypeSystemDescription()));
        //
        aeDescriptions.put("tag-assembler", TagAssembler.createDescription());
        AnalysisEngineDescription desc = PipelineDescriptorUtils
                .createAggregateDescription(aeDescriptions);
        // bind the dictionary resource
        bindExternalResource(desc,
                "morph-analyzer/" + MorphologyAnnotator.RESOURCE_KEY_DICTIONARY, morphDictDesc);
        bindExternalResource(desc,
                "tag-assembler/" + GramModelBasedTagMapper.RESOURCE_GRAM_MODEL, morphDictDesc);

        FileOutputStream out = FileUtils.openOutputStream(new File(outputPath));
        try {
            desc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}
