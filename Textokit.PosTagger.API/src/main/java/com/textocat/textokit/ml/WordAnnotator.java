/**
 *
 */
package com.textocat.textokit.ml;

import com.textocat.textokit.commons.cas.FSUtils;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.morph.fs.Wordform;
import com.textocat.textokit.segmentation.fstype.Sentence;
import com.textocat.textokit.tokenizer.fstype.NUM;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

/**
 * Auxiliary annotator that makes Word annotations for each token that can carry
 * them.
 *
 * @author Rinat Gareev
 */
public class WordAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        makeWords(jCas);
    }

    public static void makeWords(JCas jCas) {
        for (Sentence sent : JCasUtil.select(jCas, Sentence.class)) {
            for (Token tok : JCasUtil.selectCovered(jCas, Token.class, sent)) {
                if (canCarryWord(tok)) {
                    Word word = new Word(jCas);
                    word.setBegin(tok.getBegin());
                    word.setEnd(tok.getEnd());
                    word.setToken(tok);

                    Wordform wf = new Wordform(jCas);
                    wf.setWord(word);
                    word.setWordforms(FSUtils.toFSArray(jCas, wf));
                    word.addToIndexes();
                }
            }
        }
    }

    public static boolean canCarryWord(Token tok) {
        return tok instanceof W || tok instanceof NUM;
    }
}
