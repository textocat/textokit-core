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
import com.textocat.textokit.phrrecog.cas.{NounPhrase, Phrase}
import org.apache.commons.lang3.builder.{HashCodeBuilder, ToStringBuilder, ToStringStyle}

/**
 * @author Rinat Gareev
 */
class ConstraintTargetFactory(gramModel: GramModel) {

  def headFeature(featStr: String): ConstraintTarget = {
    getGramCategory(gramModel, featStr) match {
      case Some(gramIds) => new HeadGrammemeTarget(gramIds)
      case None => throw new IllegalArgumentException(
        "Unknown head constraint target: %s".format(featStr))
    }
  }

  def prepositionTarget: ConstraintTarget = PrepositionTarget
}

private[pattern] class HeadGrammemeTarget(protected val gramIds: Set[String])
  extends ConstraintTarget with GrammemeExtractor {

  override def getValue(phr: Phrase): String = extractGrammeme(phr.getHead)

  override def equals(obj: Any): Boolean = obj match {
    case that: HeadGrammemeTarget => this.gramIds == that.gramIds
    case _ => false
  }

  override def hashCode(): Int =
    new HashCodeBuilder().append(gramIds).toHashCode()

  override def toString =
    new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append(gramIds).toString
}

private[mappings] object PrepositionTarget extends ConstraintTarget {
  override def getValue(phr: Phrase): String =
    phr match {
      case np: NounPhrase =>
        val prep = np.getPreposition
        if (prep != null)
          prep.getWord.getCoveredText
        else null
      case otherPhrase => null
    }

  override def toString = "PrepositionTarget"
}