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

package com.textocat.textokit.morph.lemmatizer

import java.io.File

import com.textocat.textokit.commons.io.IoUtils
import com.textocat.textokit.commons.util.PipelineDescriptorUtils
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPIFactory
import com.textocat.textokit.postagger.PosTaggerAPI
import com.textocat.textokit.segmentation.SentenceSplitterAPI
import com.textocat.textokit.tokenizer.TokenizerAPI
import org.apache.uima.resource.metadata.MetaDataObject
import org.apache.uima.resource.metadata.impl.Import_impl

object DescriptionGenerator {

  def getDescriptionWithDep() = {
    val aeDescriptions = scala.collection.mutable.Map.empty[String, MetaDataObject]
    aeDescriptions("tokenizer") = TokenizerAPI.getAEImport()
    aeDescriptions("sentenceSplitter") = SentenceSplitterAPI.getAEImport()
    //
    val posTaggerDescImport = new Import_impl()
    posTaggerDescImport.setName("pos_tagger")
    aeDescriptions("pos-tagger") = posTaggerDescImport
    //
    aeDescriptions("lemmatizer") = Lemmatizer.createDescription()
    //
    import scala.collection.JavaConversions._
    val aggrDesc = PipelineDescriptorUtils.createAggregateDescription(aeDescriptions)
    //
    // bind MorphDictionary
    val morphDictDesc = MorphDictionaryAPIFactory.getMorphDictionaryAPI().getResourceDescriptionForCachedInstance()
    morphDictDesc.setName(PosTaggerAPI.MORPH_DICTIONARY_RESOURCE_NAME)
    PipelineDescriptorUtils.getResourceManagerConfiguration(aggrDesc).addExternalResource(morphDictDesc)
    aggrDesc
  }

  def serializeDescriptionWithDep(pathToXML: String) = {
    val description = getDescriptionWithDep()
    // TODO in JAVA 7
    // val fileWriter = Files.newBufferedWriter(Paths.get(pathToXML), StandardCharsets.UTF_8)
    val fileWriter = IoUtils.openBufferedWriter(new File(pathToXML))
    description.toXML(fileWriter)
    fileWriter.close()
  }
}
