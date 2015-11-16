/**
 *
 */
package com.textocat.textokit

/**
 * @author Rinat Gareev
 *
 */

import com.textocat.textokit.commons.cas.AnnotationOffsetComparator
import com.textocat.textokit.morph.fs.{Word, Wordform}
import com.textocat.textokit.phrrecog.cas.NounPhrase
import org.apache.uima.cas.{ArrayFS, FeatureStructure}

import scala.collection.immutable.SortedSet
import scala.math.Ordering
import scala.util.control.Breaks

package object phrrecog {

  val PhraseTypeNP = "NP"
  val PhraseTypeVP = "VP"

  val annOffsetComp = Ordering.comparatorToOrdering(
    AnnotationOffsetComparator.instance(classOf[Word]))

  val wfOffsetComp = new Ordering[Wordform] {
    override def compare(x: Wordform, y: Wordform): Int =
      annOffsetComp.compare(x.getWord, y.getWord)
  }

  /**
   * Returns the first word of NP.
   * If ignoreAux is true then leading preposition or particle is ignored.
   */
  def getFirstWord(np: NounPhrase, ignoreAux: Boolean): Word =
    toTraversable(np, ignoreAux).minBy(_.getWord.getBegin).getWord

  private[phrrecog] def toTraversable(np: NounPhrase, ignoreAux: Boolean): Traversable[Wordform] =
    new Traversable[Wordform] {
      override def foreach[U](f: Wordform => U) {
        toTraverseableLocal(np, ignoreAux).foreach(f)
        for (subNP <- traversableNPArray(np.getDependentPhrases()))
          phrrecog.toTraversable(subNP, false).foreach(f(_))
      }
    }

  private def toTraverseableLocal[U](np: NounPhrase, ignoreAux: Boolean): Traversable[Wordform] =
    new Traversable[Wordform] {
      override def foreach[U](f: Wordform => U) {
        f(np.getHead)
        if (!ignoreAux && np.getPreposition != null) f(np.getPreposition)
        if (!ignoreAux && np.getParticle != null) f(np.getParticle)
        np.getDependentWords() match {
          case null =>
          case depsFS => for (i <- 0 until depsFS.size)
            f(depsFS.get(i).asInstanceOf[Wordform])
        }
      }
    }

  private def toTraverseableLocal[U](np: NounPhrase): Traversable[Wordform] =
    toTraverseableLocal(np, false)

  // TODO low priority: move to scala-uima-common utility package
  def fsArrayToTraversable[FST <: FeatureStructure](
                                                     fsArr: ArrayFS, fstClass: Class[FST]): Traversable[FST] = new Traversable[FST] {
    override def foreach[U](f: FST => U): Unit =
      if (fsArr != null)
        for (i <- 0 until fsArr.size)
          f(fsArr.get(i).asInstanceOf[FST])
  }

  def traversableNPArray(npArr: ArrayFS): Traversable[NounPhrase] =
    fsArrayToTraversable(npArr, classOf[NounPhrase])

  /**
   * Returns the last word of NP.
   * If ignoreAux is true then leading preposition or particle is ignored.
   */
  def getLastWord(np: NounPhrase, ignoreAux: Boolean): Word =
    toTraversable(np, ignoreAux).maxBy(_.getWord.getBegin).getWord

  def getWords(np: NounPhrase, ignoreAux: Boolean): SortedSet[Word] =
    SortedSet.empty[Word](annOffsetComp) ++ toTraversable(np, ignoreAux).map(_.getWord)

  def getOffsets(np: NounPhrase): (Int, Int) =
    (getFirstWord(np, false).getBegin, getLastWord(np, false).getEnd)

  def containWord(np: NounPhrase, w: Word): Boolean =
    toTraversable(np, false).exists(_.getWord == w)

  /**
   * @return None if tree of given np does not contain given word.
   *         Else return list where head is a sub-np containing given word and tail is ancestor NPs chain.
   */
  def getDependencyChain(np: NounPhrase, w: Word): Option[List[NounPhrase]] = {
    require(w != null, "w is NULL")
    def searchLocal(ancestorChain: List[NounPhrase], np: NounPhrase): Option[List[NounPhrase]] = {
      if (toTraverseableLocal(np).exists(_.getWord == w)) Some(np :: ancestorChain)
      else {
        val breaks = new Breaks
        import breaks.{break, breakable}
        var result: Option[List[NounPhrase]] = None
        breakable {
          for (subNP <- traversableNPArray(np.getDependentPhrases)) {
            result = searchLocal(np :: ancestorChain, subNP)
            if (result.isDefined)
              break
          }
        }
        result
      }
    }
    searchLocal(Nil, np)
  }
}