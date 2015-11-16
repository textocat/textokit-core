package com.textocat.textokit.morph.lemmatizer

import com.textocat.textokit.commons.util.PipelineDescriptorUtils
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPIFactory
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.postagger.PosTaggerAPI
import com.textocat.textokit.segmentation.SentenceSplitterAPI
import com.textocat.textokit.tokenizer.TokenizerAPI
import org.apache.uima.cas.FeatureStructure
import org.apache.uima.fit.factory.ExternalResourceFactory
import org.apache.uima.fit.pipeline.SimplePipeline
import org.apache.uima.fit.util.JCasUtil.select
import org.apache.uima.util.CasCreationUtils
import org.scalatest._

import scala.collection.JavaConversions._

/**
 * Created by fsqcds on 07/05/14.
 */
class LemmatizerSpec extends FlatSpec with Matchers {
  "Lemmatizer" should "generate correct lemmas" in {
    val lemmatizerDesc = Lemmatizer.createDescription()
    val aggregateDesc = PipelineDescriptorUtils.createAggregateDescription(
      // descriptions
      TokenizerAPI.getAEImport() :: SentenceSplitterAPI.getAEImport() :: PosTaggerAPI.getAEImport()
        :: lemmatizerDesc :: Nil,
      // names
      "tokenizer" :: "sentenc-splitter" :: "pos-tagger" :: "lemmatizer" :: Nil)
    // add dictionary
    val extDictDesc = MorphDictionaryAPIFactory.getMorphDictionaryAPI.getResourceDescriptionForCachedInstance
    extDictDesc.setName(PosTaggerAPI.MORPH_DICTIONARY_RESOURCE_NAME)
    PipelineDescriptorUtils.getResourceManagerConfiguration(aggregateDesc).addExternalResource(extDictDesc)
    ExternalResourceFactory.bindExternalResource(aggregateDesc,
      "lemmatizer/" + Lemmatizer.ResourceKeyDictionary, PosTaggerAPI.MORPH_DICTIONARY_RESOURCE_NAME)
    // 
    val jCas = CasCreationUtils.createCas(aggregateDesc).getJCas
    jCas.setDocumentText("Душа моя озарена неземной радостью. Oracle купил Sun")
    SimplePipeline.runPipeline(jCas, aggregateDesc)
    val lemmas = select(jCas, classOf[Word]).flatMap((word: Word) => {
      word.getWordforms.toArray.map((wordformFS: FeatureStructure) => {
        wordformFS.asInstanceOf[Wordform].getLemma
      })
    })
    lemmas should be(List("душа", "мой", "озарён", "неземной", "радость", "oracle", "купил", "sun"))
  }
}
