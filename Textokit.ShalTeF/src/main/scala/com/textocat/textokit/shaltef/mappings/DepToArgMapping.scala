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

import com.textocat.textokit.shaltef.mappings.pattern.PhrasePattern
import org.apache.commons.lang3.builder.{HashCodeBuilder, ToStringBuilder, ToStringStyle}
import org.apache.uima.cas.{Feature, Type}

import scala.collection.immutable.Iterable

/**
 * @author Rinat Gareev
 */
trait DepToArgMapping {

  val triggerLemmaIds: Set[Int]

  val templateAnnoType: Type

  val slotMappings: Iterable[SlotMapping]
}

class SlotMapping(val pattern: PhrasePattern, val isOptional: Boolean,
                  val slotFeatureOpt: Option[Feature]) {
  override def equals(obj: Any): Boolean = obj match {
    case that: SlotMapping => this.pattern == that.pattern && this.isOptional == that.isOptional &&
      this.slotFeatureOpt == that.slotFeatureOpt
    case _ => false
  }

  override def hashCode(): Int =
    new HashCodeBuilder().append(pattern).append(isOptional).append(slotFeatureOpt).toHashCode()

  override def toString(): String = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
    append(slotFeatureOpt).append(isOptional).append(pattern).toString
}