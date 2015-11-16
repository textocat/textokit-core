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
package com.textocat.textokit.phrrecog.util

import com.textocat.textokit.commons.consumer.XmiWriter
import com.textocat.textokit.commons.cpe.FileDirectoryCollectionReader
import com.textocat.textokit.commons.util.DocumentUtils
import com.textocat.textokit.segmentation.SentenceSplitterAPI
import com.textocat.textokit.tokenizer.TokenizerAPI
import org.apache.uima.fit.factory.AnalysisEngineFactory._
import org.apache.uima.fit.factory.CollectionReaderFactory
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory._
import org.apache.uima.fit.pipeline.SimplePipeline

/**
 * @author Rinat Gareev
 *
 */
object StandoffAnnotationsToXmi {

  def main(args: Array[String]) {
    if (args.length != 3) {
      println("Usage: <annoStrParserFactoryClass> <inputDir> <outputDir>")
      sys.exit(1)
    }
    val annoStrParserFactoryClsName = args(0)
    val inputDir = args(1)
    val outputDir = args(2)

    val colReaderDesc = {
      import FileDirectoryCollectionReader._
      val tsDesc = createTypeSystemDescription(DocumentUtils.TYPESYSTEM_COMMONS)
      CollectionReaderFactory.createDescription(classOf[FileDirectoryCollectionReader],
        tsDesc,
        PARAM_DIRECTORY_PATH, inputDir)
    }

    val tokenizerDesc = TokenizerAPI.getAEDescription()

    val paraSplitterDesc = {
      val tsDesc = SentenceSplitterAPI.getTypeSystemDescription()
      // createEngineDescription(classOf[ParagraphSplitter], tsDesc)
      ???
    }

    val standoffParserDesc = {
      import StandoffAnnotationsProcessor._
      val tsDesc = createTypeSystemDescription(
        "com.textocat.textokit.morph.morphology-ts",
        "com.textocat.textokit.phrrecog.ts-phrase-recognizer")
      createPrimitiveDescription(classOf[StandoffAnnotationsProcessor], tsDesc,
        ParamAnnotationStringParserFactoryClass, annoStrParserFactoryClsName)
    }

    val xmiWriterDesc = {
      import XmiWriter._
      createPrimitiveDescription(classOf[XmiWriter],
        PARAM_OUTPUTDIR, outputDir)
    }
    SimplePipeline.runPipeline(colReaderDesc,
      tokenizerDesc, paraSplitterDesc, standoffParserDesc, xmiWriterDesc)
  }

}