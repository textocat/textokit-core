/**
 *
 */
package com.textocat.textokit.phrrecog.parsing

import com.textocat.textokit.commons.cas.FSUtils._
import com.textocat.textokit.morph.fs.{Word, Wordform}
import org.apache.uima.fit.util.FSCollectionFactory

import scala.collection.JavaConversions._

/**
 * @author Rinat Gareev
 *
 */
object WordUtils {

  def checkGrammems(w: Word, pos: String, grms: GrammemeMatcher*): Boolean = {
    require(w != null, "word annotation is null")
    if (w.getWordforms() == null) false
    else FSCollectionFactory.create(w.getWordforms(), classOf[Wordform]).exists { wf =>
      val gramSet = toSet(wf.getGrammems())
      // TODO more robust check for Part-of-Speech
      gramSet.contains(pos) && grms.forall {
        case GrammemeRequired(gr) => gramSet.contains(gr)
        case GrammemeProhibited(gr) => !gramSet.contains(gr)
      }
    }
  }

  def findWordform(w: Word, pos: String, grms: GrammemeMatcher*): Option[Wordform] = {
    require(w != null, "word annotation is null")
    if (w.getWordforms() == null) None
    else FSCollectionFactory.create(w.getWordforms(), classOf[Wordform]).find { wf =>
      val gramSet = toSet(wf.getGrammems())
      // TODO more robust check for Part-of-Speech
      gramSet.contains(pos) && grms.forall {
        case GrammemeRequired(gr) => gramSet.contains(gr)
        case GrammemeProhibited(gr) => !gramSet.contains(gr)
      }
    }
  }

  def has(gr: String) = new GrammemeRequired(gr)

  def hasNot(gr: String) = new GrammemeProhibited(gr)
}

sealed abstract class GrammemeMatcher

case class GrammemeRequired(gr: String) extends GrammemeMatcher

case class GrammemeProhibited(gr: String) extends GrammemeMatcher