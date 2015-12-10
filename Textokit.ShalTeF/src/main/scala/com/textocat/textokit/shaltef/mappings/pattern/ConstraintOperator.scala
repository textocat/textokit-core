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
import com.textocat.textokit.phrrecog.fsArrayToTraversable

/**
 * @author Rinat Gareev
 */
sealed trait ConstraintOperator

trait UnaryConstraintOperator {
  def apply(phr: Phrase, arg: Any): Boolean
}

trait BinaryConstraintOperator {
  def apply(leftArg: Any, rightArg: Any): Boolean
}

case object Equals extends BinaryConstraintOperator {
  override def apply(leftArg: Any, rightArg: Any): Boolean =
    leftArg == rightArg
}

case object HasHeadsPath extends UnaryConstraintOperator {
  def apply(phr: Phrase, arg: Any): Boolean =
    arg match {
      case paths: Set[Iterable[String]] => paths.exists(apply(phr, _))
      case path: Iterable[String] => matches(phr, path.toList)
      case u => throw new IllegalStateException("Can't apply HasHeadsPath to arg %s".format(u))
    }

  private def matches(phr: Phrase, expectedHeads: List[String]): Boolean =
    if (expectedHeads.isEmpty) true
    else if (phr == null) false
    else if (getHeadLemma(phr) == expectedHeads.head) {
      val depPhrases = fsArrayToTraversable(phr.getDependentPhrases, classOf[Phrase])
      if (depPhrases.isEmpty) matches(null, expectedHeads.tail)
      else depPhrases.exists(matches(_, expectedHeads.tail))
    } else false

  private def getHeadLemma(phr: Phrase): String = phr.getHead.getLemma match {
    case null => phr.getHead.getWord.getCoveredText
    case str => str
  }
}