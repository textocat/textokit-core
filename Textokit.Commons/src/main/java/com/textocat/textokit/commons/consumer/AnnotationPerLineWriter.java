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

package com.textocat.textokit.commons.consumer;

import com.textocat.textokit.commons.io.IoUtils;
import com.textocat.textokit.commons.util.AnnotatorUtils;
import com.textocat.textokit.commons.util.DocumentUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.resource.ResourceInitializationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Rinat Gareev
 */
public class AnnotationPerLineWriter extends CasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription(
            String targetTypeName, Path outputDir, String outputFileSuffix) throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(AnnotationPerLineWriter.class,
                PARAM_TARGET_TYPE, targetTypeName,
                PARAM_OUTPUT_DIR, outputDir.toString(),
                PARAM_OUTPUT_FILE_SUFFIX, outputFileSuffix);
    }

    private static final String PARAM_TARGET_TYPE = "targetType";
    private static final String PARAM_OUTPUT_DIR = "outputDir";
    private static final String PARAM_OUTPUT_FILE_SUFFIX = "outputFileSuffix";

    @ConfigurationParameter(name = PARAM_TARGET_TYPE)
    private String targetTypeName;
    @ConfigurationParameter(name = PARAM_OUTPUT_DIR)
    private String _outputDir;
    @ConfigurationParameter(name = PARAM_OUTPUT_FILE_SUFFIX)
    private String outputFileSuffix;
    // state fields
    private Type targetType;
    private Path outputDir;

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        super.initialize(ctx);
        //
        outputDir = Paths.get(_outputDir);
        try {
            outputDir = Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void typeSystemInit(TypeSystem ts) throws AnalysisEngineProcessException {
        super.typeSystemInit(ts);
        //
        targetType = ts.getType(targetTypeName);
        AnnotatorUtils.annotationTypeExist(targetTypeName, targetType);
    }

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        String docUriStr = DocumentUtils.getDocumentUri(cas);
        if (docUriStr == null) {
            throw new IllegalStateException("Can't extract document URI");
        }
        Path docUriPath = IoUtils.extractPathFromURI(docUriStr);
        if (docUriPath.isAbsolute()) {
            docUriPath = Paths.get("/").relativize(docUriPath);
        }
        Path outputPath = outputDir.resolve(docUriPath);
        outputPath = IoUtils.addExtension(outputPath, outputFileSuffix);
        try (PrintWriter out = IoUtils.openPrintWriter(outputPath.toFile())) {
            for (AnnotationFS anno : CasUtil.select(cas, targetType)) {
                String text = anno.getCoveredText();
                text = StringUtils.replaceChars(text, "\r\n", "  ");
                out.println(text);
            }
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}
