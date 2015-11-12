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

package com.textocat.textokit.segmentation.heur;

import com.textocat.textokit.segmentation.fstype.Paragraph;
import com.textocat.textokit.tokenizer.fstype.BREAK;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * @author Rinat Gareev
 */
public class ParagraphSplitter extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        AnnotationIndex<Annotation> lineBreakIdx = cas.getAnnotationIndex(BREAK.typeIndexID);
        int lastParaEnd = 0;
        for (Annotation lb : lineBreakIdx) {
            makeParagraphAnnotation(lastParaEnd, lb.getBegin(), cas);
            lastParaEnd = lb.getEnd();
        }
        // create anno for last paragraph
        int docLength = cas.getDocumentText().length();
        if (lastParaEnd < docLength) {
            makeParagraphAnnotation(lastParaEnd, docLength, cas);
        }
    }

    private void makeParagraphAnnotation(int begin, int end, JCas cas) {
        Paragraph result = new Paragraph(cas);
        result.setBegin(begin);
        result.setEnd(end);
        result.addToIndexes();
    }

}