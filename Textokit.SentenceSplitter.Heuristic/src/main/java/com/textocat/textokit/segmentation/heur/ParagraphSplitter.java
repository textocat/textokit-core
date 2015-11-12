/**
 *
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