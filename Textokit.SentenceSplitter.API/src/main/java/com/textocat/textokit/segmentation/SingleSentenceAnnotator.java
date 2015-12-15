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

package com.textocat.textokit.segmentation;

import com.textocat.textokit.segmentation.fstype.Sentence;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.AnnotationFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * This annotator always make a single {@link com.textocat.textokit.segmentation.fstype.Sentence} covering
 * a whole document text.
 *
 * @author Rinat Gareev
 */
public class SingleSentenceAnnotator extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription() throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(SingleSentenceAnnotator.class,
                SentenceSplitterAPI.getTypeSystemDescription());
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        Sentence sent = AnnotationFactory.createAnnotation(jCas, 0, jCas.getDocumentText().length(), Sentence.class);
        FSIterator<Token> tokenIter = jCas.getAnnotationIndex(Token.class).iterator();
        Token firstToken = null;
        Token lastToken = null;
        tokenIter.moveToFirst();
        if (tokenIter.isValid()) {
            firstToken = tokenIter.get();
            tokenIter.moveToLast();
            lastToken = tokenIter.get();
        }
        sent.setFirstToken(firstToken);
        sent.setLastToken(lastToken);
    }
}
