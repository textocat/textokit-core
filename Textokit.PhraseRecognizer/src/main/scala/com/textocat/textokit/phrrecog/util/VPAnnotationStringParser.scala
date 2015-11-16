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