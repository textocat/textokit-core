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
