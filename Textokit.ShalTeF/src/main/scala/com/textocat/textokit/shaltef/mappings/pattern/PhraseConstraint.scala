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

import com.textocat.textokit.phrrecog.cas.Phrase

/**
 * @author Rinat Gareev
 */
trait PhraseConstraint {
  def matches(phr: Phrase, ctx: MatchingContext): Boolean
}

class PhraseConstraintFactory {
  def phraseConstraint(t: ConstraintTarget, op: BinaryConstraintOperator, v: ConstraintValue): PhraseConstraint =
    BinOpPhraseConstraint(t, op, v)

  def phraseConstraint(op: UnaryConstraintOperator, v: ConstraintValue): PhraseConstraint =
    UnOpPhraseConstraint(op, v)
}

private[mappings] case class BinOpPhraseConstraint private[pattern](target: ConstraintTarget, op: BinaryConstraintOperator, value: ConstraintValue)
  extends PhraseConstraint {

  override def matches(phr: Phrase, ctx: MatchingContext): Boolean =
    op(target.getValue(phr), value.getValue(ctx))

}

private[mappings] case class UnOpPhraseConstraint private[pattern](op: UnaryConstraintOperator, value: ConstraintValue)
  extends PhraseConstraint {

  override def matches(phr: Phrase, ctx: MatchingContext): Boolean =
    op(phr, value.getValue(ctx))

}