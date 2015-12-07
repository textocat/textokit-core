package com.textocat.textokit.shaltef.mappings

import java.net.URL

import com.textocat.textokit.morph.dictionary.resource.MorphDictionary
import com.textocat.textokit.shaltef.mappings.pattern.{ConstraintTargetFactory, ConstraintValueFactory, PhraseConstraintFactory}
import org.apache.uima.cas.Type

/**
 * @author Rinat Gareev
 */
trait MappingsParser {
  def parse(url: URL, templateAnnoType: Type, mappingsHolder: DepToArgMappingsBuilder)
}

class MappingsParserConfig(val morphDict: MorphDictionary) {
  val gramModel = morphDict.getGramModel
  val constraintValueFactory = new ConstraintValueFactory(gramModel)
  val constraintTargetFactory = new ConstraintTargetFactory(gramModel)
  val constraintFactory = new PhraseConstraintFactory
}