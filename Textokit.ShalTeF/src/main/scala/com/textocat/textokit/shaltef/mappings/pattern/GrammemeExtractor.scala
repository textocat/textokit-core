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