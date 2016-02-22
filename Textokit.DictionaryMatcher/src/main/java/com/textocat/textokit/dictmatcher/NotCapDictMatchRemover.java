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

package com.textocat.textokit.dictmatcher;

import com.google.common.collect.Sets;
import com.textocat.textokit.dictmatcher.fs.DictionaryMatch;
import com.textocat.textokit.morph.fs.SimplyWord;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.tokenizer.fstype.SW;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.Set;

import static java.lang.String.format;

/**
 * Deletes annotations that start with a token with a lower-case first letter.
 *
 * @author Rinat Gareev
 */
public class NotCapDictMatchRemover extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription(
            Class<? extends DictionaryMatch> targetAnnotationClass) throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(NotCapDictMatchRemover.class,
                PARAM_TARGET_ANNOTATION_TYPE, targetAnnotationClass.getName());
    }

    public static final String PARAM_TARGET_ANNOTATION_TYPE = "targetAnnotationType";

    @ConfigurationParameter(name = PARAM_TARGET_ANNOTATION_TYPE)
    private Class<? extends DictionaryMatch> targetAnnotationClass;


    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        Set<DictionaryMatch> dm2Delete = Sets.newHashSet();
        for (DictionaryMatch dm : JCasUtil.select(jCas, targetAnnotationClass)) {
            Token firstToken = getFirstToken(dm);
            if (firstToken instanceof SW) {
                dm2Delete.add(dm);
            }
        }
        for (DictionaryMatch dm : dm2Delete) {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(format(
                        "Dictionary match '%s' is deleted by NotCap rule", dm.getCoveredText()));
            }
            dm.removeFromIndexes();
        }
    }

    private static Token getFirstToken(DictionaryMatch dm) {
        Annotation _firstToken = dm.getFirstToken();
        if (_firstToken == null) {
            throw new UnsupportedOperationException();
        }
        if (_firstToken instanceof Token) {
            return (Token) _firstToken;
        }
        if (_firstToken instanceof SimplyWord) {
            return ((SimplyWord) _firstToken).getToken();
        }
        if (_firstToken instanceof Word) {
            return ((Word) _firstToken).getToken();
        }
        throw new UnsupportedOperationException(
                format("Unknown token annotation type: %s", _firstToken.getType()));
    }
}
