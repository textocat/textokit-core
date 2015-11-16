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
package com.textocat.textokit.phrrecog

import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.phrrecog.cas.NounPhrase
import com.textocat.textokit.phrrecog.input.AnnotationSpan
import com.textocat.textokit.phrrecog.parsing.{NP, NPParsers}
import com.textocat.textokit.segmentation.fstype.Sentence
import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.fit.util.{FSCollectionFactory, JCasUtil}
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.cas.FSArray
import org.apache.uima.jcas.tcas.Annotation

import scala.collection.JavaConversions._
import scala.collection.immutable.TreeSet
import scala.util.parsing.input.Reader

/**
 * @author Rinat Gareev
 *
 */
class NPRecognizer extends JCasAnnotator_ImplBase with NPParsers {

  override def process(jCas: JCas): Unit = {
    JCasUtil.select(jCas, classOf[Sentence]).foreach(processSpan(jCas, _))
  }

  private def processSpan(jCas: JCas, span: Annotation) {
    val spanWords = JCasUtil.selectCovered(jCas, classOf[Word], span).toList
    if (spanWords.nonEmpty)
      parseFrom(new AnnotationSpan(spanWords).reader)
  }

  private def parseFrom(reader: Reader[Word]): Unit =
    if (!reader.atEnd)
      np(reader) match {
        case Success(np, rest) =>
          addToCas(np)
          parseFrom(rest)
        case Failure(_, _) =>
          // start from next anno
          parseFrom(reader.rest)
      }

  private def addToCas(np: NP) {
    val npAnno = createNPAnnotation(np)
    npAnno.addToIndexes()
  }

  private def createNPAnnotation(np: NP): NounPhrase = {
    val head = np.noun
    val jCas = head.getCAS.getJCas

    val phrase = new NounPhrase(jCas)
    phrase.setBegin(head.getWord.getBegin)
    phrase.setEnd(head.getWord.getEnd)

    phrase.setHead(head)
    np.prepOpt match {
      case Some(prep) => phrase.setPreposition(prep)
      case None =>
    }
    np.particleOpt match {
      case Some(particle) => phrase.setParticle(particle)
      case None =>
    }

    val depWordsFsArray = new FSArray(jCas, np.depWords.size)
    FSCollectionFactory.fillArrayFS(depWordsFsArray, TreeSet.empty[Wordform](wfOffsetComp) ++ np.depWords)
    phrase.setDependentWords(depWordsFsArray)

    // TODO low priority: add a sanity check to avoid infinite recursion
    val depAnnoQ = np.depNPs.map(createNPAnnotation)
    val depNPsFsArray = new FSArray(jCas, depAnnoQ.size)
    FSCollectionFactory.fillArrayFS(depNPsFsArray, depAnnoQ)
    phrase.setDependentPhrases(depNPsFsArray)

    phrase
  }
}