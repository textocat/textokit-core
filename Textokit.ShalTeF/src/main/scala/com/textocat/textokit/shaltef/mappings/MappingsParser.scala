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