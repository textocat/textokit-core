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

package com.textocat.textokit.morph

import java.{util => ju}

import com.textocat.textokit.commons.cas.FSUtils
import com.textocat.textokit.morph.dictionary.resource.{GramModel, MorphDictionaryUtils}
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.morph.model.MorphConstants._
import com.textocat.textokit.postagger.MorphCasUtils
import org.apache.uima.fit.util.JCasUtil
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.tcas.Annotation

import scala.collection.JavaConversions._

/**
 * @author Rinat Gareev
 */
case class RichWord(wordform: Wordform, text: String, lemma: Option[String], partOfSpeech: String, gramCats: Map[String, String]) {
  def anno = wordform.getWord

  def begin = anno.getBegin

  def end = anno.getEnd
}

object RichWord {
  val NoPartOfSpeech = "null"
}

class RichWordFactory(gramModel: GramModel) {

  import RichWord._

  // TODO note that this is too specific, bind to RNC dictionary extension
  private val PartOfSpeechRoots = Set(POST, Abbr, Anum, Apro, Prnt)

  private val partOfSpeechMask = {
    val mask = new ju.BitSet()
    PartOfSpeechRoots.map(gramModel.getGrammemWithChildrenBits(_, true)).foreach(mask.or)
    mask
  }

  private val gramCatMasks = List(ANim, GNdr, NMbr, CAse, ASpc, TRns, PErs, TEns, MOod, INvl, VOic)
    .map { gcat => gcat -> gramModel.getGrammemWithChildrenBits(gcat, true)
  }.toMap + (POST -> partOfSpeechMask)

  def seqFromSpan(jCas: JCas, span: Annotation): IndexedSeq[RichWord] =
    JCasUtil.selectCovered(jCas, classOf[Word], span).map(word2RichWord).toIndexedSeq

  implicit def word2RichWord(w: Word): RichWord = {
    val wf = MorphCasUtils.requireOnlyWordform(w)
    val wfbs = MorphDictionaryUtils.toGramBits(gramModel, FSUtils.toList(wf.getGrammems))
    val partOfSpeech = {
      val grs = wfbs.clone().asInstanceOf[ju.BitSet]
      grs.and(partOfSpeechMask)
      if (grs.isEmpty) NoPartOfSpeech
      else gramModel.toGramSet(grs).mkString("&")
    }
    val gramCats = (for ((cat, catMask) <- gramCatMasks) yield {
      val grs = wfbs.clone().asInstanceOf[ju.BitSet]
      grs.and(catMask)
      if (grs.isEmpty) None
      else Some(cat -> gramModel.toGramSet(grs).head)
    }).flatten.toMap
    RichWord(wf, w.getCoveredText, Option(wf.getLemma), partOfSpeech, gramCats)
  }
}
