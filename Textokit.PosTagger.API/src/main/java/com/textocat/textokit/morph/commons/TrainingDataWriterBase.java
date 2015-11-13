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

package com.textocat.textokit.morph.commons;

import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.postagger.MorphCasUtils;
import com.textocat.textokit.segmentation.fstype.Sentence;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author Rinat Gareev
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public abstract class TrainingDataWriterBase extends JCasAnnotator_ImplBase {

    public static final String PARAM_OUTPUT_DIR = "outputDir";
    public static final String TRAINING_DATA_FILENAME = "training-data.txt";

    // config
    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, mandatory = true)
    protected File outputDir;
    // state fields
    protected PrintWriter outputWriter;
    // per-CAS state fields
    private Map<Token, Word> token2WordIndex;

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        super.initialize(ctx);
        //
        File outputFile = new File(outputDir, TRAINING_DATA_FILENAME);
        try {
            OutputStream os = FileUtils.openOutputStream(outputFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
            outputWriter = new PrintWriter(bw);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
            token2WordIndex = MorphCasUtils.getToken2WordIndex(jCas);
            // process each sentence
            for (Sentence sent : JCasUtil.select(jCas, Sentence.class)) {
                process(jCas, sent);
            }
        } finally {
            token2WordIndex = null;
        }
    }

    private void process(JCas jCas, Sentence sent) throws AnalysisEngineProcessException {
        List<Token> tokens = JCasUtil.selectCovered(Token.class, sent);
        if (tokens.isEmpty()) {
            return;
        }
        processSentence(jCas, tokens);
    }

    protected abstract void processSentence(JCas jCas, List<Token> tokens)
            throws AnalysisEngineProcessException;

    protected Word getWordOfToken(Token token) {
        return token2WordIndex.get(token);
    }

    @Override
    public void collectionProcessComplete() throws AnalysisEngineProcessException {
        closeWriter();
        super.collectionProcessComplete();
    }

    @Override
    public void destroy() {
        closeWriter();
        super.destroy();
    }

    private void closeWriter() {
        if (outputWriter != null) {
            IOUtils.closeQuietly(outputWriter);
            outputWriter = null;
        }
    }
}
