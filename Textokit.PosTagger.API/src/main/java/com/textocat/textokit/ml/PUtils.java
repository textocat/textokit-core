/**
 *
 */
package com.textocat.textokit.ml;

import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;

import java.util.List;

/**
 * @author Rinat Gareev
 */
class PUtils {

    public static Word getWordAnno(JCas view, Annotation focusAnnotation)
            throws CleartkExtractorException {
        Word wordAnno = null;
        if (focusAnnotation instanceof Word) {
            wordAnno = (Word) focusAnnotation;
        } else if (focusAnnotation instanceof Token) {
            List<Word> wordsCovered = JCasUtil.selectCovered(Word.class, focusAnnotation);
            if (!wordsCovered.isEmpty()) {
                wordAnno = wordsCovered.get(0);
            }
        } else {
            throw CleartkExtractorException.wrongAnnotationType(Word.class, focusAnnotation);
        }
        return wordAnno;
    }

    private PUtils() {
    }
}
