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