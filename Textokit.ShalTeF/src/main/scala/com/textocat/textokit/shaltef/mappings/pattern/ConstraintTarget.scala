package com.textocat.textokit.shaltef.mappings.pattern

import com.textocat.textokit.phrrecog.cas.Phrase

/**
 * @author Rinat Gareev
 */
trait ConstraintTarget {

  def getValue(phr: Phrase): Any

}