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

package com.textocat.textokit.morph.lemmatizer

import com.textocat.textokit.morph.dictionary.WordUtils
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.morph.model.{Wordform => DictWordform}
import org.apache.uima.cas.FeatureStructure
import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.fit.descriptor.ExternalResource
import org.apache.uima.fit.factory.AnalysisEngineFactory
import org.apache.uima.fit.util.JCasUtil.select
import org.apache.uima.jcas.JCas

import scala.collection.JavaConversions._

/**
 * Created by fsqcds on 07/05/14.
 */
class Lemmatizer extends JCasAnnotator_ImplBase {
  // TODO how to assign a scala val to a Java annotation attribute?
  @ExternalResource(key = "morphDictionary", mandatory = true)
  private var dictHolder: MorphDictionaryHolder = null

  def jaccardCoef(first: Set[String], second: Set[String]) = {
    (first & second).size.toDouble / (first | second).size
  }

  def findLemma(wordform: Wordform): Option[String] = {
    val dict = dictHolder.getDictionary
    val wordText = WordUtils.normalizeToDictionaryForm(wordform.getWord.getCoveredText)
    val entries = dict.getEntries(wordText)
    val targetGrammems = wordform.getGrammems

    if (entries.size > 0 && targetGrammems != null) {
      val lemmaId = dict.getEntries(wordText).maxBy((dictWf: DictWordform) => {
        val wfGrammems: Set[String] = dict.getGramModel().toGramSet(dictWf.getGrammems).toSet
        jaccardCoef(targetGrammems.toArray.toSet, wfGrammems)
      }).getLemmaId
      Some(dictHolder.getDictionary.getLemma(lemmaId).getString)
    } else {
      None
    }
  }

  def process(aJCAS: JCas) {
    select(aJCAS, classOf[Word]).foreach((word: Word) => {
      word.getWordforms.toArray.foreach((wordformFS: FeatureStructure) => {
        val wordform = wordformFS.asInstanceOf[Wordform]
        findLemma(wordform) match {
          case Some(lemma) => wordform.setLemma(lemma)
          case None =>
        }
      })
    })
  }
}

object Lemmatizer {
  val ResourceKeyDictionary = "morphDictionary"

  def createDescription() = AnalysisEngineFactory.createEngineDescription(classOf[Lemmatizer],
    LemmatizerAPI.getTypeSystemDescription)
}