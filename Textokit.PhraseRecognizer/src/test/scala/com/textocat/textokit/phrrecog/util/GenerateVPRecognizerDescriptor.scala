/**
 *
 */
package com.textocat.textokit.phrrecog.util

import java.io.{BufferedOutputStream, FileOutputStream}

import com.textocat.textokit.phrrecog.VPRecognizer
import org.apache.uima.fit.factory.AnalysisEngineFactory._
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory._

/**
 * @author Rinat Gareev
 *
 */
object GenerateVPRecognizerDescriptor {

  def main(args: Array[String]): Unit = {
    val importedTsDesc = createTypeSystemDescription("com.textocat.textokit.phrrecog.ts-phrase-recognizer")
    val nprDesc = createEngineDescription(classOf[VPRecognizer], importedTsDesc)
    val os = new BufferedOutputStream(
      new FileOutputStream(
        s"src/main/resources/${classOf[VPRecognizer].getName.replace('.', '/')}.xml"))
    try {
      nprDesc.toXML(os, true)
    } finally {
      os.close()
    }
  }

}