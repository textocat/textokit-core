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