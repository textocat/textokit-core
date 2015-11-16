/**
 *
 */
package com.textocat.textokit.morph

import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.morph.model.{MorphConstants => M}
import com.textocat.textokit.tokenizer.fstype.NUM
import org.apache.uima.cas.text.AnnotationIndex
import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.cas.{FSArray, StringArray}

import scala.collection.JavaConversions.iterableAsScalaIterable

/**
 * @author Rinat Gareev
 *
 */
class NumProcessor extends JCasAnnotator_ImplBase {

  override def process(jCas: JCas) {
    for (num <- jCas.getAnnotationIndex(NUM.typeIndexID).asInstanceOf[AnnotationIndex[NUM]])
      makeWordFrom(jCas, num)
  }

  private def makeWordFrom(jCas: JCas, num: NUM) {
    val word = new Word(jCas)
    word.setBegin(num.getBegin)
    word.setEnd(num.getEnd)
    word.setToken(num)

    // make wordforms
    val numrWf = new Wordform(jCas)
    numrWf.setPos(M.NUMR)
    numrWf.setWord(word)

    val adjWf = new Wordform(jCas)
    adjWf.setPos(M.ADJF)
    val adjWfGrammems = new StringArray(jCas, 1)
    adjWfGrammems.set(0, M.Anum)
    adjWf.setGrammems(adjWfGrammems)
    adjWf.setWord(word)

    val wfArr = new FSArray(jCas, 2)
    wfArr.set(0, numrWf)
    wfArr.set(1, adjWf)
    word.setWordforms(wfArr)

    word.addToIndexes()
  }

}