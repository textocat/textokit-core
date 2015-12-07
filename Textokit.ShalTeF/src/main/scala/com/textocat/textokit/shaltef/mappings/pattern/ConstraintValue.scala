package com.textocat.textokit.shaltef.mappings.pattern

/**
 * @author Rinat Gareev
 */
trait ConstraintValue {

  def getValue(ctx: MatchingContext): Any

}