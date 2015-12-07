package com.textocat.textokit.shaltef.mappings.pattern

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import com.textocat.textokit.phrrecog.cas.Phrase

/**
 * @author Rinat Gareev
 */
trait ConstraintTarget {

  def getValue(phr: Phrase): Any

}