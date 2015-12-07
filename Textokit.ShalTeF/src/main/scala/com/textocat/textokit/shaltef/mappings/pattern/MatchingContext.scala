package com.textocat.textokit.shaltef.mappings.pattern

import com.textocat.textokit.morph.fs.Wordform

/**
 * @author Rinat Gareev
 */
trait MatchingContext {

  def triggerHead: Wordform

}

private[mappings] class DefaultMatchingContext(val trigger: Wordform) extends MatchingContext {
  val triggerHead = trigger
}

object MatchingContext {
  def apply(trigger: Wordform): MatchingContext = new DefaultMatchingContext(trigger)
}