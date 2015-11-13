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

package com.textocat.textokit.morph.ruscorpora;

import com.google.common.collect.Sets;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.postagger.MorphCasUtils;
import com.textocat.textokit.tokenizer.fstype.NUM;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.Map;
import java.util.Set;

import static com.textocat.textokit.commons.cas.AnnotationUtils.toPrettyString;
import static com.textocat.textokit.commons.util.DocumentUtils.getDocumentUri;

/**
 * @author Rinat Gareev
 */
public class SpecialWTokenRemover extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(SpecialWTokenRemover.class);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        Map<Token, Word> token2WordIndex = MorphCasUtils.getToken2WordIndex(jCas);
        Set<Token> tokens2Remove = Sets.newHashSet();
        for (Token token : JCasUtil.select(jCas, Token.class)) {
            Word word = token2WordIndex.get(token);
            if (word == null && (token instanceof NUM || token instanceof W)) {
                getLogger().warn(String.format(
                        "Token %s in %s does not have corresponding Word annotation",
                        toPrettyString(token), getDocumentUri(jCas)));
                tokens2Remove.add(token);
            }
        }
        for (Token token : tokens2Remove) {
            token.removeFromIndexes();
        }
    }
}
