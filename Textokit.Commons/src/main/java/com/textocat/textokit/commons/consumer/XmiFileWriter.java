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

import com.google.common.base.Function;
import com.textocat.textokit.commons.io.IoUtils;
import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.initializable.InitializableFactory;
import org.apache.uima.resource.ResourceInitializationException;
import com.textocat.textokit.commons.DocumentMetadata;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes each CAS XMI into a separate file which name is determined by a function
 * "{@link DocumentMetadata} => {@link java.nio.file.Path}". This function is configured via annotator parameters.
 * A resulting path is resolved against {@link #PARAM_OUTPUT_BASE_PATH}.
 *
 * @author Rinat Gareev (Textocat)
 */
public class XmiFileWriter extends XmiWriterBase {

    public static final String PARAM_OUTPUT_BASE_PATH = "outputBasePath";
    public static final String PARAM_OUTPUT_PATH_FUNCTION = "outputPathFunction";
    public static final String XMI_FILE_EXTENSION = "xmi";
    // config fields
    @ConfigurationParameter(name = PARAM_OUTPUT_BASE_PATH, mandatory = true)
    private String outBasePathStr;
    @ConfigurationParameter(name = PARAM_OUTPUT_PATH_FUNCTION, mandatory = false,
            defaultValue = "com.textocat.textokit.commons.consumer.DefaultSourceURI2OutputFilePathFunction")
    private Class<? extends Function> outPathFuncClass;
    // state fields
    private Path outBasePath;
    private Function<DocumentMetadata, Path> outPathFunc;

    public static AnalysisEngineDescription createDescription(File outputDir) throws ResourceInitializationException {
        return createDescription(outputDir.toPath());
    }

    public static AnalysisEngineDescription createDescription(Path outputBasePath)
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(XmiFileWriter.class,
                PARAM_OUTPUT_BASE_PATH, outputBasePath.toString());
    }

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        super.initialize(ctx);
        //
        outBasePath = Paths.get(outBasePathStr);
        //noinspection unchecked
        outPathFunc = InitializableFactory.create(ctx, outPathFuncClass);
    }

    @Override
    protected OutputStream getOutputStream(DocumentMetadata meta) throws IOException {
        Path outRelativePath = outPathFunc.apply(meta);
        Path resultPath = outBasePath.resolve(outRelativePath);
        resultPath = IoUtils.addExtension(resultPath, XMI_FILE_EXTENSION);
        // does not create missing parents
        // return Files.newOutputStream(resultPath);
        return FileUtils.openOutputStream(resultPath.toFile());
    }
}
