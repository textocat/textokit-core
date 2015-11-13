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

package com.textocat.textokit.morph.util;

import com.textocat.textokit.tokenizer.fstype.TokenBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * @author Rinat Gareev
 */
public class NonTokenizedSpanAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String txt = jCas.getDocumentText();
        if (txt == null || txt.isEmpty()) {
            return;
        }
        int txtLength = txt.length();
        AnnotationIndex<Annotation> tokenIndex = jCas
                .getAnnotationIndex(TokenBase.typeIndexID);
        FSIterator<Annotation> tokenIter = tokenIndex.iterator();
        if (!tokenIter.isValid()) {
            makeNTSpan(jCas, 0, txtLength);
        } else {
            int lastTokenEnd = 0;
            while (tokenIter.isValid()) {
                Annotation token = tokenIter.get();
                if (token.getBegin() > lastTokenEnd) {
                    makeNTSpan(jCas, lastTokenEnd, token.getBegin());
                }
                lastTokenEnd = token.getEnd();
                //
                tokenIter.moveToNext();
            }
            // check the span after the last token
            if (txtLength > lastTokenEnd) {
                makeNTSpan(jCas, lastTokenEnd, txtLength);
            }
        }
    }

    private void makeNTSpan(JCas jCas, int begin, int end) {
        assert end > begin;
        new NonTokenizedSpan(jCas, begin, end).addToIndexes();
    }
}