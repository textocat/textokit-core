package com.textocat.textokit.shaltef.mappings

import com.textocat.textokit.morph.dictionary.resource.{GramModel, MorphDictionary}
import com.textocat.textokit.morph.fs.Wordform
import com.typesafe.scalalogging.slf4j.StrictLogging

import scala.collection.JavaConversions._

/**
 * @author Rinat Gareev
 */
package object pattern extends StrictLogging {

  private[pattern] val packageLogger = logger

  val grammemeAliases = Map("case" -> "CAse", "gndr" -> "GNdr")

  private[pattern] def getGramCategory(gramModel: GramModel, gcId: String): Option[Set[String]] = {
    // lookup alias
    val gramCat = grammemeAliases.get(gcId) match {
      case Some(gc) => gc
      case None => gcId
    }
    gramModel.getGrammemWithChildrenBits(gramCat, false) match {
      case null => None
      case gramBS =>
        val gramIds = gramModel.toGramSet(gramBS)
        if (gramIds == null || gramIds.isEmpty)
          throw new IllegalStateException("Empty grammeme set for gramCat: %s".format(gramCat))
        Some(gramIds.toSet)
    }
  }

  type WordformConstraint = Wordform => Boolean

  def lemmaWfConstraint(lemmaStr: String): WordformConstraint = _.getLemma == lemmaStr

  def stringWfConstraint(str: String): WordformConstraint = _.getWord.getCoveredText == str
}