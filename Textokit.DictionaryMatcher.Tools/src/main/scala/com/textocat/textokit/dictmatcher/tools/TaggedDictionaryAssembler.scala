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

package com.textocat.textokit.dictmatcher.tools

import java.io.File

import com.google.common.collect.Sets
import com.textocat.textokit.commons.cas.FSUtils
import com.textocat.textokit.commons.io.IoUtils
import com.textocat.textokit.commons.util.PipelineDescriptorUtils
import com.textocat.textokit.morph.commons.SimplyWordAnnotator
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPIFactory
import com.textocat.textokit.morph.fs.SimplyWord
import com.textocat.textokit.morph.lemmatizer.LemmatizerAPI
import com.textocat.textokit.morph.model.MorphConstants._
import com.textocat.textokit.postagger.PosTaggerAPI
import com.textocat.textokit.segmentation.SingleSentenceAnnotator
import com.textocat.textokit.tokenizer.TokenizerAPI
import com.typesafe.config.ConfigFactory
import org.apache.uima.analysis_engine.AnalysisEngine
import org.apache.uima.fit.factory.AnalysisEngineFactory
import org.apache.uima.fit.util.JCasUtil

import scala.collection.JavaConversions._
import scala.collection.immutable.ListMap
import scala.io.Source

/**
 * <p>
 * An app that takes a set of input files in the format specified below and produces
 * an output plain text file which is ready to use by {@link com.textocat.textokit.dictmatcher.TaggedChunkerBuilderResource}.
 * </p>
 * <h2>Input (text) file format:</h2>
 * <p>One dictionary entry per line.
 * </p>
 * <h2>Entry normalization</h2>
 * Each entry is normalized using a lemmatizer pipeline with fallback to original text in lower case.
 * <h2>Dictionary metadata</h2>
 * An entry is assigned a tag (see [[com.textocat.textokit.dictmatcher.TaggedChunkAnnotationAdapter]]) given by its containing file. A tag of file
 * is defined in app arguments like <code>foo.txt=>foo_tag</code>, i.e. all entries from <code>foo.txt</code> get
 * tag <code>foo_tag</code>.
 *
 * @author Rinat Gareev
 */
object TaggedDictionaryAssembler {

  val loggingPeriodicity = 1000

  def main(args: Array[String]): Unit = {
    val (cfgFile, outputFile) = args match {
      case Array(a1, a2) =>
        (new File(a1), new File(a2))
      case _ =>
        System.err.println("Usage: <cfg-file> <output-file>")
        sys.exit(1)
    }
    // read config
    val cfg = ConfigFactory.parseFile(cfgFile)
    val inputBaseDir = new File(cfg.getString("input-base-dir"))
    val fileTagTuples = cfg.getStringList("filename-tag-mapping").toList.view.map(_.split("=>")).map {
      case Array(fileName, tag) => (fileName.trim, tag.trim)
      case other =>
        System.err.println("Can't parse mapping string in the config: " + other.mkString("=>"))
        sys.exit(1)
    }.toList
    val deleteClosedSetWords = cfg.getBoolean("delete-closed-set-words")
    //
    def accept(tokens: List[SimplyWord]): Boolean = {
      !deleteClosedSetWords || tokens.size != 1 || {
        val grams = FSUtils.toSet(tokens.head.getGrammems)
        Sets.intersection(grams, ClosedSetGrams).isEmpty
      }
    }
    // prepare output
    val out = IoUtils.openPrintWriter(outputFile)
    try {
      // prepare normalizer
      val lemmatizerAE: AnalysisEngine = AnalysisEngineFactory.createEngine(lemmatizerAEDesc)
      val jCas = lemmatizerAE.newJCas()
      var entryCounter = 0
      //
      def addToBuilder(file: File, tag: String): Unit = {
        val fileSrc = Source.fromFile(file, "UTF-8")
        try {
          for (line <- fileSrc.getLines() if line.trim.nonEmpty && !line.startsWith("#")) {
            jCas.setDocumentText(line)
            lemmatizerAE.process(jCas)
            // TODO refactor this strategy and save with chunker
            val tokens = JCasUtil.select(jCas, classOf[SimplyWord]).toList
            if (tokens.isEmpty) {
              println("WARN: No tokens in line:\n" + line)
            } else if (!accept(tokens)) {
              println("INFO: Not accepted:\n" + line)
            } else {
              val lemmata = tokens.map(sw => sw.getLemma match {
                case null => sw.getCoveredText.toLowerCase
                case lemma => lemma
              })
              // OUTPUT
              import com.textocat.textokit.dictmatcher.TaggedChunkerBuilderResource._
              out.print(lemmata.mkString(TOKEN_SEPARATOR))
              out.print(TAG_DELIMITER)
              out.println(tag)
              //
              entryCounter += 1
              if (entryCounter % loggingPeriodicity == 0) {
                println(s"$entryCounter entries have been added...")
              }
            }
            jCas.reset()
          }
        } finally {
          fileSrc.close()
        }
      }
      //
      for ((filename, tag) <- fileTagTuples) {
        addToBuilder(new File(inputBaseDir, filename), tag)
      }
    } finally {
      out.close()
    }
    //
    println(s"Finished. The result file size is ${outputFile.length() / 1024} Kb")
  }

  private val ClosedSetGrams = Sets.newHashSet(PRCL, CONJ, PREP, NPRO, Apro)

  private val lemmatizerAEDesc = {
    val desc = PipelineDescriptorUtils.createAggregateDescription(ListMap(
      "tokenizer" -> TokenizerAPI.getAEImport,
      "sentence-splitter" -> SingleSentenceAnnotator.createDescription(),
      "pos-tagger" -> PosTaggerAPI.getAEImport,
      "lemmatizer" -> LemmatizerAPI.getAEImport,
      "sw-maker" -> SimplyWordAnnotator.createDescription()
    ))
    val morphDictDesc = MorphDictionaryAPIFactory.getMorphDictionaryAPI.getResourceDescriptionForCachedInstance
    morphDictDesc.setName(PosTaggerAPI.MORPH_DICTIONARY_RESOURCE_NAME)
    PipelineDescriptorUtils.getResourceManagerConfiguration(desc).addExternalResource(morphDictDesc)
    desc
  }
}
