/**
 *
 */
package com.textocat.textokit.phrrecog.util

import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.fit.util.{JCasUtil, CasUtil}
import org.apache.uima.jcas.JCas
import java.io.File
import com.textocat.textokit.commons.cas.AnnotationUtils
import com.textocat.textokit.commons.DocumentMetadata
import java.net.URI
import org.apache.commons.io.FilenameUtils
import scala.io.Source
import scala.collection.JavaConversions._
import com.textocat.textokit.segmentation.fstype.Paragraph
import org.apache.uima.cas.text.AnnotationFS
import scala.collection.mutable.ListBuffer
import StandoffAnnotationsProcessor._
import com.textocat.textokit.morph.fs.Word
import org.apache.uima.jcas.cas.FSArray
import java.util.regex.Pattern
import com.textocat.textokit.tokenizer.fstype.Token
import org.apache.uima.cas.Type
import com.textocat.textokit.phrrecog.cas.Phrase
import org.apache.uima.UimaContext
import com.textocat.textokit.commons.util.AnnotatorUtils._
import scala.collection.mutable.HashMap

/**
 * @author Rinat Gareev
 *
 */
class StandoffAnnotationsProcessor extends JCasAnnotator_ImplBase {

  // state
  private var tokenType: Type = _
  private var annoStrParserFactory: PhraseStringParsersFactory = _

  override def initialize(ctx: UimaContext) {
    super.initialize(ctx)
    val annoStrParserFactoryClassName = ctx.getConfigParameterValue(ParamAnnotationStringParserFactoryClass).asInstanceOf[String]
    mandatoryParam(ParamAnnotationStringParserFactoryClass, annoStrParserFactoryClassName)
    val annoStrParserClass = Class.forName(annoStrParserFactoryClassName)
    annoStrParserFactory = annoStrParserClass.newInstance().asInstanceOf[PhraseStringParsersFactory]
  }

  override def process(jCas: JCas) {
    val annFile = getAnnFile(jCas)
    val annLines = Source.fromFile(annFile, "utf-8").getLines()
    val paraAnnotations = JCasUtil.select(jCas, classOf[Paragraph])
    tokenType = jCas.getCasType(Token.typeIndexID)
    for ((paragraph, paragrNum) <- paraAnnotations.zipWithIndex)
      if (annLines.hasNext)
        parseAnnoLine(paragraph, annLines.next())
      else throw new IllegalStateException("Standoff annotations files ends before line %s".format(paragrNum))
  }

  private def parseAnnoLine(paraAnno: AnnotationFS, annLine: String) {
    val jCas = paraAnno.getCAS().getJCas()
    val paraOffset = paraAnno.getBegin
    val phraseStrings = annLine.split("\\|")
    val paraTokens = getParagraphTokens(paraAnno)
    val annoStrParser = annoStrParserFactory.createParser(jCas, paraTokens)
    for (phrStr <- phraseStrings)
      annoStrParser.parse(phrStr).addToIndexes()
  }

  private def getParagraphTokens(paraAnno: AnnotationFS): Array[AnnotationFS] = {
    val tokensList = CasUtil.selectCovered(paraAnno.getCAS(), tokenType, paraAnno)
    tokensList.toArray(new Array[AnnotationFS](tokensList.size()))
  }

  private def getAnnFile(jCas: JCas): File = {
    val docMeta = AnnotationUtils.getSingleAnnotation(jCas, classOf[DocumentMetadata])
    if (docMeta == null) throw new IllegalStateException("No DocumentMetadata")
    val docUri = new URI(docMeta.getSourceUri())
    val docFile = new File(docUri)
    val baseName = FilenameUtils.getBaseName(docFile.getName())
    val annFile = new File(docFile.getParent(), baseName + ".ann")
    if (!annFile.isFile())
      throw new IllegalStateException("No ann file for %s".format(docFile))
    annFile
  }
}

object StandoffAnnotationsProcessor {
  val ParamAnnotationStringParserFactoryClass = "AnnotationStringParserFactoryClass"
}