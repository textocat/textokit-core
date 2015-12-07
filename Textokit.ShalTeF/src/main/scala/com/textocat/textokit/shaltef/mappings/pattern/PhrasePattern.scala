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
import org.apache.commons.lang3.builder.HashCodeBuilder

import scala.collection.immutable.Iterable

/**
 * @author Rinat Gareev
 */
trait PhrasePattern {
  def matches(phr: Phrase, ctx: MatchingContext): Boolean
}

private[mappings] class ConstraintConjunctionPhrasePattern(
                                                            val constraints: Iterable[PhraseConstraint])
  extends PhrasePattern {

  override def matches(phr: Phrase, ctx: MatchingContext): Boolean = {
    var matched = true
    val iter = constraints.iterator
    while (matched && iter.hasNext)
      matched = iter.next().matches(phr, ctx)
    matched
  }

  override def equals(obj: Any): Boolean = obj match {
    case that: ConstraintConjunctionPhrasePattern => this.constraints == that.constraints
    case _ => false
  }

  override def hashCode(): Int =
    new HashCodeBuilder().append(constraints).toHashCode()

  override def toString = new StringBuilder("ConstraintConjunction:").append(constraints).toString
}