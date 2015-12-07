package com.textocat.textokit.shaltef

import java.net.URL
import java.{util => jul}

import com.textocat.textokit.commons.util.AnnotatorUtils._
import com.textocat.textokit.morph.dictionary.resource.{GramModelHolder, MorphDictionaryHolder}
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.phrrecog
import com.textocat.textokit.phrrecog.cas.{NounPhrase, Phrase}
import com.textocat.textokit.segmentation.fstype.Sentence
import com.textocat.textokit.shaltef.ShaltefAnnotator._
import com.textocat.textokit.shaltef.mappings._
import com.textocat.textokit.shaltef.mappings.impl.EnforcePrepositionConstraintPostProcessor
import com.textocat.textokit.shaltef.mappings.pattern.MatchingContext
import org.apache.uima.UimaContext
import org.apache.uima.cas.text.AnnotationFS
import org.apache.uima.cas.{CAS, Type, TypeSystem}
import org.apache.uima.fit.component.CasAnnotator_ImplBase
import org.apache.uima.fit.descriptor.{ConfigurationParameter, ExternalResource}
import org.apache.uima.fit.util.{CasUtil, FSCollectionFactory}
import org.apache.uima.jcas.JCas

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.{collection => sc}

/**
 * @author Rinat Gareev
 */
class ShaltefAnnotator extends CasAnnotator_ImplBase {

  @ConfigurationParameter(name = ParamTemplateMappingFiles)
  private var templMappingFileStrings: Array[String] = _
  @ExternalResource(key = ResourceKeyMorphDict)
  private var morphDictHolder: MorphDictionaryHolder = _
  //
  private val templateType2File = mutable.Map.empty[String, URL]
  private var parserConfig: MappingsParserConfig = _
  private var mappingParser: MappingsParser = _
  private var mappingsHolder: DepToArgMappingsHolder = _
  // CAS types
  private var ts: TypeSystem = _
  private var wordType: Type = _

  override def initialize(ctx: UimaContext) {
    super.initialize(ctx)
    //
    for (templMappingFileStr <- templMappingFileStrings)
      templMappingFileStr.split("\\|") match {
        case Array(templateAnnoType, urlStr) =>
          templateType2File += (templateAnnoType -> new URL(urlStr))
        case otherArr => throw new IllegalArgumentException(
          "Can't parse templateFile param value %s".format(otherArr))
      }
    parserConfig = new MappingsParserConfig(morphDictHolder.getDictionary)
    mappingParser = TextualMappingsParser(parserConfig)
  }

  override def typeSystemInit(ts: TypeSystem) {
    this.ts = ts
    wordType = ts.getType(classOf[Word].getName)
    val mappingsBuilder = DepToArgMappingsBuilder()
    // parse mapping files
    for ((templateAnnoTypeName, url) <- templateType2File) {
      val templateAnnoType = ts.getType(templateAnnoTypeName)
      annotationTypeExist(templateAnnoTypeName, templateAnnoType)
      mappingParser.parse(url, templateAnnoType, mappingsBuilder)
    }
    // apply post-processors
    getMappingPostProcessors(parserConfig).foreach(_.postprocess(mappingsBuilder))
    // build final holder
    mappingsHolder = mappingsBuilder.build()
  }

  override def process(cas: CAS): Unit = process(cas.getJCas)

  private def process(jCas: JCas) {
    for {
      segm <- jCas.getAnnotationIndex(Sentence.typeIndexID)
      w <- CasUtil.selectCovered(wordType, segm).asInstanceOf[jul.List[Word]]
      if w.getWordforms != null
      wf <- FSCollectionFactory.create(w.getWordforms, classOf[Wordform])
    } if (mappingsHolder.containsTriggerLemma(wf.getLemmaId))
      onIndicatorWordformDetected(segm, w, wf)
  }

  private def onIndicatorWordformDetected(segm: AnnotationFS, word: Word, wf: Wordform) {
    val cas = segm.getView
    // build phrase index - optimization point: reuse for the same segment segm
    val phraseIndex = buildPhraseIndex(segm, word)
    for (mapping <- mappingsHolder.getMappingsTriggeredBy(wf)) {
      val template = cas.createAnnotation(
        mapping.templateAnnoType, segm.getBegin, segm.getEnd)
      val matchedPhrases = mutable.Set.empty[Phrase]
      def fillTemplate(iter: Iterator[SlotMapping]): Boolean =
        if (iter.hasNext) {
          val slotMapping = iter.next()
          val matchingCtx = MatchingContext(wf)
          phraseIndex.searchPhrase(
            slotMapping.pattern.matches(_, matchingCtx), matchedPhrases) match {
            case None =>
              if (slotMapping.isOptional) fillTemplate(iter)
              else false
            case Some(mPhrase) =>
              slotMapping.slotFeatureOpt match {
                case Some(slotFeature) => template.setFeatureValue(
                  slotFeature,
                  makeCoveringAnnotation(slotFeature.getRange, mPhrase))
                case None => // means mapping constraint is mandatory but do not fill feature
              }
              matchedPhrases += mPhrase
              fillTemplate(iter)
          }
        } else true // END of fillTemplate
      // invoke recursive inner function
      if (fillTemplate(mapping.slotMappings.iterator))
        cas.addFsToIndexes(template)
    }
  }

  // TODO LOW: rework template slot filling actions
  private def makeCoveringAnnotation(coverType: Type, phraseAnno: Phrase): AnnotationFS = {
    val cas = phraseAnno.getView
    val (begin, end) = phraseAnno match {
      case np: NounPhrase => phrrecog.getOffsets(np)
      // TODO LOW
      case other => throw new UnsupportedOperationException
    }
    cas.createAnnotation(coverType, begin, end)
  }

  private def buildPhraseIndex(segm: AnnotationFS, refWord: Word) = new PhraseIndex(segm, refWord, ts)

  // CONFIGURATION POINT
  private def getMappingPostProcessors(config: MappingsParserConfig): Traversable[DepToArgMappingsPostProcessor] =
    List(new EnforcePrepositionConstraintPostProcessor(config))

}

object ShaltefAnnotator {
  final val ParamTemplateMappingFiles = "TemplateMappingFiles"
  final val ResourceKeyMorphDict = "MorphDict"
}

private[shaltef] class PhraseIndex(segm: AnnotationFS, refWord: Word, ts: TypeSystem) {
  private val npType: Type = ts.getType(classOf[NounPhrase].getName)
  // refWordIndex points to the closest phrase on the left to refWord;
  // if there is no such phrase in the segment then refWordIndex = -1
  val (phraseSeq: Seq[Phrase], refWordIndex) = {
    val buffer = ListBuffer.empty[NounPhrase]
    for (np <- CasUtil.selectCovered(npType, segm).asInstanceOf[jul.List[NounPhrase]])
      phrrecog.getDependencyChain(np, refWord) match {
        case Some(refWordDepChain) =>
          // TODO keep head chain of refWord separately
          val refWordNP = refWordDepChain.head
          buffer ++= phrrecog.traversableNPArray(refWordNP.getDependentPhrases)
        case None => buffer += np
      }
    val refWordIndex = buffer.indexWhere(refWord.getBegin < _.getBegin) match {
      case -1 => buffer.size - 1 // means refWord is on right to the last phrase
      case i => i - 1
    }
    (buffer, refWordIndex)
  }

  // EXTENSION POINT: introduce traverse strategy
  private val phraseTraverseSeq = {
    val (beforeRefSeq, afterRefSeq) = phraseSeq.splitAt(refWordIndex + 1)
    val buffer = ListBuffer.empty[Phrase]
    val iterBefore = beforeRefSeq.iterator
    val iterAfter = afterRefSeq.iterator
    while (iterBefore.hasNext || iterAfter.hasNext) {
      if (iterAfter.hasNext) buffer += iterAfter.next()
      if (iterBefore.hasNext) buffer += iterBefore.next()
    }
    buffer.toList
  }

  def searchPhrase(pred: Phrase => Boolean, setOfIgnored: sc.Set[Phrase]): Option[Phrase] =
    phraseTraverseSeq.find(candPhr =>
      !setOfIgnored.contains(candPhr) && pred(candPhr))
}