package com.textocat.textokit.shaltef.util

import java.io.{BufferedOutputStream, FileOutputStream}

import com.textocat.textokit.commons.cas.FSUtils
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.phrrecog.cas.{NounPhrase, Phrase}
import com.textocat.textokit.segmentation.SentenceSplitterAPI
import com.textocat.textokit.segmentation.fstype.Sentence
import com.textocat.textokit.tokenizer.TokenizerAPI
import com.textocat.textokit.tokenizer.fstype.W
import org.apache.uima.cas.impl.XmiCasSerializer
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory._
import org.apache.uima.fit.util.FSCollectionFactory._
import org.apache.uima.jcas.JCas
import org.apache.uima.jcas.cas.StringArray
import org.apache.uima.util.CasCreationUtils

import scala.collection.JavaConversions.{asJavaCollection, iterableAsScalaIterable}
import scala.collection.mutable

/**
 * @author Rinat Gareev
 */
class NprCasBuilder(val text: String, additionalTypeSystemNames: List[String]) {

  val ts = {
    val tsNames = "com.textocat.textokit.phrrecog.ts-phrase-recognizer" ::
      TokenizerAPI.TYPESYSTEM_TOKENIZER ::
      SentenceSplitterAPI.TYPESYSTEM_SENTENCES ::
      additionalTypeSystemNames
    val tsDesc = createTypeSystemDescription(tsNames: _*)
    val dumbCas = CasCreationUtils.createCas(tsDesc, null, null)
    dumbCas.getTypeSystem
  }
  val cas = CasCreationUtils.createCas(ts, null, null, null)
  cas.setDocumentText(text)
  val jCas = cas.getJCas

  private val wfMap = mutable.Map.empty[String, Wordform]

  def w(begin: Int, end: Int): Wordform = w(text.substring(begin, end), begin, end)

  def w(id: String, begin: Int, end: Int): Wordform = {
    require(!wfMap.contains(id), "Duplicate id: %s".format(id))
    val token = new W(jCas)
    token.setBegin(begin)
    token.setEnd(end)
    token.addToIndexes()
    val word = new Word(jCas)
    word.setBegin(begin)
    word.setEnd(end)
    word.setToken(token)

    val wf = new Wordform(jCas)
    wf.setWord(word)
    word.setWordforms(FSUtils.toFSArray(jCas, wf))

    word.addToIndexes()

    wfMap(id) = wf
    wf
  }

  def w(id: String): Wordform =
    if (id == null) null
    else wfMap(id)

  def np(headId: String, prepId: String = null, particleId: String = null,
         depWordIds: Iterable[String] = Nil, depNPs: Iterable[Phrase] = Nil,
         index: Boolean = false): NounPhrase = {
    val npAnno = new NounPhrase(jCas)
    val head = w(headId)
    npAnno.setBegin(head.getWord.getBegin)
    npAnno.setEnd(head.getWord.getEnd)
    npAnno.setHead(head)
    npAnno.setParticle(w(particleId))
    npAnno.setPreposition(w(prepId))
    if (depWordIds.nonEmpty)
      npAnno.setDependentWords(createFSArray(jCas, depWordIds.map(w)))
    if (depNPs.nonEmpty)
      npAnno.setDependentPhrases(createFSArray(jCas, depNPs))
    if (index)
      npAnno.addToIndexes()
    npAnno
  }

  def sent(begin: Int, end: Int): Sentence = {
    val sentAnno = new Sentence(jCas)
    sentAnno.setBegin(begin)
    sentAnno.setEnd(end)
    sentAnno.addToIndexes()
    sentAnno
  }

  def serialize(outPath: String) {
    val os = new BufferedOutputStream(new FileOutputStream(outPath))
    try {
      XmiCasSerializer.serialize(cas, null, os, true, null)
    } finally {
      os.close()
    }
  }

  implicit def wf2GrammemeBuilder(wf: Wordform): GrammemeBuilder = new GrammemeBuilder(wf)

  class GrammemeBuilder(wf: Wordform) {
    def addGrammems(grs: String*): Wordform =
      if (grs.isEmpty) wf
      else {
        wf.getWord.removeFromIndexes()
        val oldGrs = FSUtils.toSet(wf.getGrammems)
        wf.setGrammems(createStringArray(oldGrs ++ grs))
        wf.getWord.addToIndexes()
        wf
      }
  }

  private def createStringArray(strs: Iterable[String]): StringArray =
    NprCasBuilder.createStringArray(jCas, strs)
}

object NprCasBuilder {
  // TODO move to util package
  def createStringArray(jCas: JCas, strs: Iterable[String]): StringArray = {
    val result = new StringArray(jCas, strs.size)
    val strIter = strs.iterator
    for (i <- 0 until strs.size)
      result.set(i, strIter.next())
    result
  }
}