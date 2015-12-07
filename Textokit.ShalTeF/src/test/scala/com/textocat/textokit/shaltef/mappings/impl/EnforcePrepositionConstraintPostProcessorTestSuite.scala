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
package com.textocat.textokit.shaltef.mappings.impl

import java.util.BitSet

import com.textocat.textokit.morph.dictionary.resource.{GramModel, MorphDictionary}
import com.textocat.textokit.shaltef.mappings.pattern._
import com.textocat.textokit.shaltef.mappings.{DepToArgMappingsBuilder, MappingsParserConfig, SlotMapping}
import org.apache.uima.cas.{Feature, Type}
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

import scala.collection.JavaConversions.seqAsJavaList

/**
 * @author Rinat Gareev
 *
 */
class EnforcePrepositionConstraintPostProcessorTestSuite extends FunSuite with MockitoSugar {

  test("Enforce preposition constraint in the case of its absense in a pattern") {
    val morphDict = mock[MorphDictionary]
    val gramModel = mock[GramModel]
    when(morphDict.getGramModel).thenReturn(gramModel)
    val someGrBS = new BitSet
    someGrBS.set(100)
    when(gramModel.getGrammemWithChildrenBits("someGr", false)).thenReturn(someGrBS)
    when(gramModel.toGramSet(someGrBS)).thenReturn(List("v1", "v2"))

    val constrValueFactory = new ConstraintValueFactory(gramModel)
    val constrTargetFactory = new ConstraintTargetFactory(gramModel)
    val constrFactory = new PhraseConstraintFactory
    val parserCfg = new MappingsParserConfig(morphDict)

    import constrFactory._
    import constrTargetFactory._
    import constrValueFactory._
    // prepare non-empty builder
    val mpBuilder = DepToArgMappingsBuilder()
    val type1 = mock[Type]
    val feat1 = mock[Feature]
    val type2 = mock[Type]
    val feat2 = mock[Feature]

    val pattern1 = new ConstraintConjunctionPhrasePattern(
      phraseConstraint(headFeature("someGr"), Equals, constant("someGrValue")) :: Nil)
    val pattern2 = new ConstraintConjunctionPhrasePattern(
      phraseConstraint(HasHeadsPath, constantCollectionAlternatives(Set(List("someHead")))) ::
        phraseConstraint(prepositionTarget, Equals, constant("somePrep")) :: Nil)
    mpBuilder.add(new DefaultDepToArgMapping(type1, Set(1, 3, 5),
      new SlotMapping(pattern1, false, Some(feat1)) :: new SlotMapping(pattern2, false, None) :: Nil))

    val pattern3 = new ConstraintConjunctionPhrasePattern(
      phraseConstraint(HasHeadsPath, constantCollectionAlternatives(
        Set(List("oneHead"), List("anotherHead")))) :: Nil)
    mpBuilder.add(new DefaultDepToArgMapping(type2, Set(2, 4, 5),
      new SlotMapping(pattern3, false, Some(feat2)) :: Nil))

    val postProcessor = new EnforcePrepositionConstraintPostProcessor(parserCfg)
    postProcessor.postprocess(mpBuilder)

    val resultMappings = mpBuilder.getMappings
    assert(resultMappings.size === 2)
    val rmIter = resultMappings.iterator

    val pattern1Fixed = new ConstraintConjunctionPhrasePattern(
      phraseConstraint(prepositionTarget, Equals, constant(null)) ::
        phraseConstraint(headFeature("someGr"), Equals, constant("someGrValue")) :: Nil)
    assert(rmIter.next() === new DefaultDepToArgMapping(type1, Set(1, 3, 5),
      new SlotMapping(pattern1Fixed, false, Some(feat1)) :: new SlotMapping(pattern2, false, None) :: Nil))

    val pattern3Fixed = new ConstraintConjunctionPhrasePattern(
      phraseConstraint(prepositionTarget, Equals, constant(null)) ::
        phraseConstraint(HasHeadsPath, constantCollectionAlternatives(
          Set(List("oneHead"), List("anotherHead")))) :: Nil)

    assert(rmIter.next() === new DefaultDepToArgMapping(type2, Set(2, 4, 5),
      new SlotMapping(pattern3Fixed, false, Some(feat2)) :: Nil))
  }

}