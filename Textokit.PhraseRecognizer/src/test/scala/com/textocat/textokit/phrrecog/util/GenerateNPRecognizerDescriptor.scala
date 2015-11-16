/**
 *
 */
package com.textocat.textokit.phrrecog.util

import java.io.{BufferedOutputStream, FileOutputStream}

import com.textocat.textokit.phrrecog.NPRecognizer
import org.apache.uima.fit.factory.AnalysisEngineFactory._
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory._

/**
 * @author Rinat Gareev
 *
 */
object GenerateNPRecognizerDescriptor {

  def main(args: Array[String]): Unit = {
    val nprImportedTsDesc = createTypeSystemDescription("com.textocat.textokit.phrrecog.ts-phrase-recognizer")
    val nprDesc = createEngineDescription(classOf[NPRecognizer], nprImportedTsDesc)
    val os = new BufferedOutputStream(
      new FileOutputStream(
        s"src/main/resources/${classOf[NPRecognizer].getName.replace('.', '/')}.xml"))
    try {
      nprDesc.toXML(os, true)
    } finally {
      os.close()
    }
  }

}