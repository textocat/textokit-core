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

package com.textocat.textokit.phrrecog

import com.textocat.textokit.morph.dictionary.MorphDictionaryAPIFactory
import com.textocat.textokit.morph.dictionary.resource.GramModel
import com.textocat.textokit.morph.model.{MorphConstants => M}
import com.textocat.textokit.morph.{RichWord, RichWordFactory}
import com.textocat.textokit.phrrecog.VPRecognizer._
import com.textocat.textokit.phrrecog.cas.VerbPhrase
import com.textocat.textokit.segmentation.fstype.Sentence
import org.apache.uima.UimaContext
import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.fit.util.JCasUtil
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.cas.FSArray
import org.apache.uima.jcas.tcas.Annotation

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * @author Rinat Gareev
 *
 */
class VPRecognizer extends JCasAnnotator_ImplBase {

  private var gramModel: GramModel = _
  private var richWordFactory: RichWordFactory = _

  override def initialize(ctx: UimaContext): Unit = {
    super.initialize(ctx)
    gramModel = MorphDictionaryAPIFactory.getMorphDictionaryAPI.getGramModel
    richWordFactory = new RichWordFactory(gramModel)
  }

  override def process(jCas: JCas) {
    JCasUtil.select(jCas, classOf[Sentence]).foreach(processSpan(jCas, _))
  }

  private def processSpan(jCas: JCas, spanAnno: Annotation) {
    val words = richWordFactory.seqFromSpan(jCas, spanAnno)
    val verbalWfIndices =
      for (
        (wf, wfIndex) <- words.zipWithIndex
        if VerbalPoSes.contains(wf.partOfSpeech)
      ) yield wfIndex
    val attached = mutable.HashSet.empty[RichWord]
    for (verbalWfIdx <- verbalWfIndices.reverse; w = words(verbalWfIdx); if !attached.contains(w)) {
      val phrWfs = w.partOfSpeech match {
        case M.VERB => handleFiniteVerb(words, verbalWfIdx)
        case M.PRTS => handleShortPerfective(words, verbalWfIdx)
        case M.PRTF => List(w) // TODO handleFullPerfective(words, wIndex, wfIndex)
        case M.GRND => List(w) // TODO handleGerund(words, wIndex, wfIndex)
        case M.INFN => handleInfinitive(words, verbalWfIdx)
        case unknownPos => throw new UnsupportedOperationException("Unknown verbal word pos: %s"
          .format(unknownPos))
      }
      if (phrWfs.nonEmpty) {
        attached ++= phrWfs
        createPhraseAnnotation(jCas, phrWfs)
      }
    }
  }

  // returns phrase wordforms; first wf of iter is a head of phrase
  private def handleFiniteVerb(words: IndexedSeq[RichWord],
                               wfIdx: Int): Iterable[RichWord] = List(words(wfIdx))

  private def handleShortPerfective(words: IndexedSeq[RichWord],
                                    spIndex: Int): Iterable[RichWord] = {
    val sp = words(spIndex)
    if (spIndex > 0) {
      val toBeIdx = spIndex - 1
      val toBe = words(toBeIdx)
      if (toBe.lemma.contains("есть"))
        toBe :: sp :: Nil
      else sp :: Nil
    }
    else sp :: Nil
  }

  private def handleInfinitive(words: IndexedSeq[RichWord],
                               infIndex: Int): Iterable[RichWord] = {
    val inf = words(infIndex)
    // search to the left
    if (infIndex > 0) {
      // TODO elaborate
      words.view(0, infIndex).reverse.find(M.VERB == _.partOfSpeech) match {
        case Some(w) => w :: inf :: Nil
        case None => inf :: Nil
      }
    } else inf :: Nil
  }

  private def createPhraseAnnotation(jCas: JCas, phraseWords: Iterable[RichWord]) {
    val iter = phraseWords.iterator
    val head = iter.next()
    val depsFsArray = new FSArray(jCas, phraseWords.size - 1)
    var depsI = 0
    while (iter.hasNext) {
      depsFsArray.set(depsI, iter.next().wordform)
      depsI += 1
    }
    val phrase = new VerbPhrase(jCas)
    phrase.setBegin(phraseWords.view.map(_.begin).min)
    phrase.setEnd(phraseWords.view.map(_.end).max)
    phrase.setHead(head.wordform)
    phrase.setDependentWords(depsFsArray)
    phrase.addToIndexes()
  }

}

object VPRecognizer {
  val VerbalPoSes = Set(M.VERB, M.INFN, M.PRTF, M.PRTS, M.GRND)
}
