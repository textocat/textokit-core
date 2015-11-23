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

package com.textocat.textokit.phrrecog.util

import com.textocat.textokit.commons.cas.FSUtils
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.phrrecog
import com.textocat.textokit.phrrecog.cas.{NounPhrase, Phrase}
import com.textocat.textokit.phrrecog.util.NPAnnotationStringParser._
import com.textocat.textokit.phrrecog.wfOffsetComp
import com.typesafe.scalalogging.StrictLogging
import org.apache.uima.cas.text.AnnotationFS
import org.apache.uima.fit.util.FSCollectionFactory
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.cas.FSArray

import scala.collection.JavaConversions.asJavaIterable
import scala.collection.Map
import scala.collection.immutable.TreeSet

class NPAnnotationStringParserFactory extends PhraseStringParsersFactory {
  override def createParser(jCas: JCas, tokens: Array[AnnotationFS]): NPAnnotationStringParser =
    new NPAnnotationStringParser(jCas, tokens)
}

class NPAnnotationStringParser(protected val jCas: JCas, protected val tokens: Array[AnnotationFS])
  extends PhraseStringParsers with StrictLogging {

  override protected def createAnnotation(prefixedWordformsMap: Map[String, Seq[Wordform]], depPhrases: Seq[Phrase]): NounPhrase = {
    val unprefixedWfs = prefixedWordformsMap.get(null) match {
      case Some(list) => list
      case None => throw new IllegalStateException(
        "No head in %s".format(prefixedWordformsMap))
    }
    val prepWfOpt = prefixedWordformsMap.get(PrefixPreposition) match {
      case None => None
      case Some(Seq(prepWf)) => Some(prepWf)
      case Some(list) => {
        val sortedList = TreeSet.empty[Wordform](wfOffsetComp) ++ list
        val prepWord = new Word(jCas)
        prepWord.setBegin(sortedList.firstKey.getWord.getBegin)
        prepWord.setEnd(sortedList.lastKey.getWord.getEnd)
        logger.info("Compound preposition detected: %s".format(prepWord.getCoveredText))

        val prepWf = new Wordform(jCas)
        prepWf.setWord(prepWord)
        prepWord.setWordforms(FSUtils.toFSArray(jCas, prepWf))
        Some(prepWf)
      }
    }
    val particleWfOpt = prefixedWordformsMap.get(PrefixParticle) match {
      case None => None
      case Some(Seq(particleWf)) => Some(particleWf)
      case Some(list) => throw new IllegalStateException(
        "Multiple words with particle prefix: %s".format(prefixedWordformsMap))
    }
    if (prefixedWordformsMap.size > 3) throw new IllegalStateException(
      "Unknown prefixes in %s".format(prefixedWordformsMap))

    val headWf = unprefixedWfs.head

    val dependentWfAnnos = TreeSet.empty[Wordform](wfOffsetComp) ++ unprefixedWfs.tail
    val depWordformsFsArray = new FSArray(jCas, dependentWfAnnos.size)
    FSCollectionFactory.fillArrayFS(depWordformsFsArray, dependentWfAnnos)

    val depPhrasesFsArray = new FSArray(jCas, depPhrases.size)
    FSCollectionFactory.fillArrayFS(depPhrasesFsArray, depPhrases)

    val phrase = new NounPhrase(jCas)
    if (prepWfOpt.isDefined) phrase.setPreposition(prepWfOpt.get)
    if (particleWfOpt.isDefined) phrase.setParticle(particleWfOpt.get)
    phrase.setHead(headWf)
    phrase.setDependentWords(depWordformsFsArray)
    phrase.setDependentPhrases(depPhrasesFsArray)
    val (begin, end) = phrrecog.getOffsets(phrase)
    phrase.setBegin(begin)
    phrase.setEnd(end)
    phrase
  }
}

object NPAnnotationStringParser {
  val PrefixPreposition = "prep"
  val PrefixParticle = "prcl"
}