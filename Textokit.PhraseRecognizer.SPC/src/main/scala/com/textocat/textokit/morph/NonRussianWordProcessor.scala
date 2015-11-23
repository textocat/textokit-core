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

/**
 *
 */
package com.textocat.textokit.morph

import com.textocat.textokit.morph.fs.Word
import com.textocat.textokit.tokenizer.fstype.W
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import org.apache.uima.cas.text.AnnotationIndex
import org.apache.uima.fit.util.CasUtil
import org.apache.uima.jcas.JCas

import scala.collection.JavaConversions.iterableAsScalaIterable

/**
 * @author Rinat Gareev
 *
 */
class NonRussianWordProcessor extends JCasAnnotator_ImplBase {

  def process(jCas: JCas) {
    val wordType = jCas.getCasType(Word.typeIndexID)
    for (w <- jCas.getAnnotationIndex(W.typeIndexID).asInstanceOf[AnnotationIndex[W]])
      if (CasUtil.selectCovered(wordType, w).isEmpty())
        makeWordFor(jCas, w)
  }

  private def makeWordFor(jCas: JCas, w: W) {
    val word = new Word(jCas)
    word.setBegin(w.getBegin)
    word.setEnd(w.getEnd)
    word.setToken(w)
    word.addToIndexes()
  }

}