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

package com.textocat.textokit.shaltef.mappings.pattern

import com.textocat.textokit.morph.dictionary.resource.GramModel

/**
 * @author Rinat Gareev
 */
class ConstraintValueFactory(gramModel: GramModel) {
  def constant(valueString: String): ConstraintValue = ConstantScalarValue(valueString)

  def constantCollection(values: Iterable[String]): ConstraintValue = ConstantCollectionValue(values)

  def constantCollectionAlternatives(colValues: Set[Iterable[String]]): ConstraintValue =
    ConstantCollectionAlternatives(colValues)

  def triggerFeatureReference(refString: String): ConstraintValue = {
    getGramCategory(gramModel, refString) match {
      case Some(gramCatSet) => TriggerGrammemeReference(gramCatSet)
      case None => throw new IllegalArgumentException(
        "Unknown trigger feature reference: %s".format(refString))
    }
  }
}

private[pattern] case class ConstantScalarValue(valueString: String) extends ConstraintValue {
  override def getValue(ctx: MatchingContext) = valueString
}

private[pattern] case class ConstantCollectionValue(values: Iterable[String])
  extends ConstraintValue {
  override def getValue(ctx: MatchingContext) = values
}

private[pattern] case class ConstantCollectionAlternatives(colValues: Set[Iterable[String]])
  extends ConstraintValue {
  override def getValue(ctx: MatchingContext) = colValues
}

private[pattern] case class TriggerGrammemeReference(gramIds: Set[String])
  extends ConstraintValue with GrammemeExtractor {

  override def getValue(ctx: MatchingContext): String = extractGrammeme(ctx.triggerHead)
}