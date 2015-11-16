/**
 *
 */
package com.textocat.textokit.morph.lemmatizer

import java.io.File

import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.uima.fit.factory.ExternalResourceFactory

/**
 * @author Rinat Gareev
 *
 */
object GenerateDescriptionForLemmatizerAPI {

  def main(args: Array[String]): Unit = {
    val outputFile = new File("src/main/resources/" + LemmatizerAPI.AE_LEMMATIZER.replace('.', '/') + ".xml")
    val desc = Lemmatizer.createDescription()
    ExternalResourceFactory.bindExternalResource(desc,
      Lemmatizer.ResourceKeyDictionary, LemmatizerAPI.MORPH_DICTIONARY_RESOURCE_NAME)
    val out = FileUtils.openOutputStream(outputFile)
    try {
      desc.toXML(out)
    } finally {
      IOUtils.closeQuietly(out)
    }
  }

}