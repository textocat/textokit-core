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

import com.textocat.textokit.shaltef.mappings._
import com.textocat.textokit.shaltef.mappings.pattern._
import com.typesafe.scalalogging.slf4j.StrictLogging

/**
 * @author Rinat Gareev
 *
 */
class EnforcePrepositionConstraintPostProcessor(config: MappingsParserConfig) extends DepToArgMappingsPostProcessor
with StrictLogging {

  import config.constraintFactory._
  import config.constraintTargetFactory._
  import config.constraintValueFactory._

  override def postprocess(mpBuilder: DepToArgMappingsBuilder) {
    for (mp <- mpBuilder.getMappings) {
      val newMP = enforcePrepositionConstraints(mp)
      if (mp != newMP) {
        mpBuilder.replace(mp, newMP)
        logger.info("Preposition constraint enforced in:\n%s".format(newMP))
      }
    }
  }

  private def enforcePrepositionConstraints(mp: DepToArgMapping): DepToArgMapping = {
    new DefaultDepToArgMapping(mp.templateAnnoType, mp.triggerLemmaIds,
      mp.slotMappings.toList.map(enforcePrepositionConstraint))
  }

  private def enforcePrepositionConstraint(sm: SlotMapping): SlotMapping =
    new SlotMapping(enforcePrepositionConstraint(sm.pattern), sm.isOptional, sm.slotFeatureOpt)

  private def enforcePrepositionConstraint(p: PhrasePattern): PhrasePattern =
    p match {
      case conj: ConstraintConjunctionPhrasePattern =>
        if (conj.constraints.exists(isPrepositionConstraint))
          conj
        else
          new ConstraintConjunctionPhrasePattern(phraseConstraint(
            prepositionTarget, Equals, constant(null))
            :: conj.constraints.toList)
      case _ => throw new UnsupportedOperationException("Can't post-process pattern: %s".format(p))
    }

  private def isPrepositionConstraint(pc: PhraseConstraint) =
    pc match {
      case binPC: BinOpPhraseConstraint => binPC.target == PrepositionTarget
      case _ => false
    }
}