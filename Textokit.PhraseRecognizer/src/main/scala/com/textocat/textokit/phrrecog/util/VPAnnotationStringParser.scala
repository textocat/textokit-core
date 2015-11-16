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

import com.textocat.textokit.morph.fs.Wordform
import com.textocat.textokit.phrrecog.cas.{Phrase, VerbPhrase}
import org.apache.uima.cas.text.AnnotationFS
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.cas.FSArray

import scala.collection.Map

class VPAnnotationStringParserFactory extends PhraseStringParsersFactory {
  override def createParser(jCas: JCas, tokens: Array[AnnotationFS]) =
    new VPAnnotationStringParser(jCas, tokens)
}

class VPAnnotationStringParser(
                                protected val jCas: JCas,
                                protected val tokens: Array[AnnotationFS])
  extends PhraseStringParsers {

  protected override def createAnnotation(
                                           prefixedWordformsMap: Map[String, Seq[Wordform]],
                                           depPhrases: Seq[Phrase]): VerbPhrase = {
    val unprefixedWfs = prefixedWordformsMap.get(null) match {
      case Some(list) => list
      case None => throw new IllegalStateException(
        "No head in %s".format(prefixedWordformsMap))
    }
    if (prefixedWordformsMap.size > 1) throw new IllegalStateException(
      "Unknown prefixes in %s".format(prefixedWordformsMap))
    val headWf = unprefixedWfs.head
    val dependentWfAnnos = unprefixedWfs.tail

    val dependentsFsArray = new FSArray(jCas, dependentWfAnnos.size)
    var fsArrayIndex = 0
    for (dwAnno <- dependentWfAnnos) {
      dependentsFsArray.set(fsArrayIndex, dwAnno)
      fsArrayIndex += 1
    }

    val phrase = new VerbPhrase(jCas)
    phrase.setBegin(headWf.getWord.getBegin)
    phrase.setEnd(headWf.getWord.getEnd)
    phrase.setHead(headWf)
    phrase.setDependentWords(dependentsFsArray)

    phrase
  }

}