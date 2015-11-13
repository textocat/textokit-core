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
