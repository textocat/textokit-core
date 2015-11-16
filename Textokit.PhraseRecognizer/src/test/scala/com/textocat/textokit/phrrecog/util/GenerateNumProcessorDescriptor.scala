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