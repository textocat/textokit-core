package com.textocat.textokit.shaltef.mappings.pattern

import com.textocat.textokit.commons.cas.FSUtils
import com.textocat.textokit.morph.fs.Wordform

import scala.collection.JavaConversions.asScalaSet

/**
 * @author Rinat Gareev
 */
private[pattern] trait GrammemeExtractor {

  // means to be ids of target grammatical category
  protected val gramIds: Set[String]

  protected def extractGrammeme(wf: Wordform): String = {
    wf.getGrammems match {
      case null => null
      case triggerGramsFsArr =>
        val allTriggerGrams = FSUtils.toSet(triggerGramsFsArr)
        val triggerGrams = gramIds.intersect(allTriggerGrams)
        if (triggerGrams.isEmpty) null
        else {
          val result = triggerGrams.head
          if (triggerGrams.size > 1)
            packageLogger.warn("Too much grammems sharing the same category: %s".format(triggerGrams))
          result
        }
    }
  }

}