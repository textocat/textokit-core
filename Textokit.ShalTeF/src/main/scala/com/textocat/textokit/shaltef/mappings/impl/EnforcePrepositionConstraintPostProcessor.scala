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