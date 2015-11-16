/**
 *
 */
package com.textocat.textokit.phrrecog.util

import java.io.{BufferedOutputStream, FileOutputStream}

import com.textocat.textokit.morph.NumProcessor
import com.textocat.textokit.postagger.PosTaggerAPI
import org.apache.uima.fit.factory.AnalysisEngineFactory._
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory._

/**
 * @author Rinat Gareev
 *
 */
object GenerateNumProcessorDescriptor {

  def main(args: Array[String]): Unit = {
    val importedTsDesc = createTypeSystemDescription(PosTaggerAPI.TYPESYSTEM_POSTAGGER)
    val nprDesc = createEngineDescription(classOf[NumProcessor], importedTsDesc)
    val os = new BufferedOutputStream(
      new FileOutputStream(
        s"src/main/resources/${classOf[NumProcessor].getName.replace('.', '/')}.xml"))
    try {
      nprDesc.toXML(os, true)
    } finally {
      os.close()
    }
  }

}