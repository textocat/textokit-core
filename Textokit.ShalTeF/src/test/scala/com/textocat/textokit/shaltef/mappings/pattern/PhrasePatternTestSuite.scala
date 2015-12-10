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

import com.textocat.textokit.shaltef.util.NprCasBuilder
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

class PhrasePatternTestSuite extends FunSuite with MockitoSugar {

  private val text = "foobar"

  test("ConstraintConjunctionPhrasePattern matching") {
    val cb = new NprCasBuilder(text, Nil)
    import cb._
    val trigger = w("trigger", 0, 1)
    val np1 = {
      w("head1", 5, 6)
      np("head1", index = true)
    }
    val emptyPP = new ConstraintConjunctionPhrasePattern(Nil)
    val ctx = MatchingContext(trigger)
    assert(emptyPP.matches(np1, ctx))

    val pc1 = mock[PhraseConstraint]
    val pc2 = mock[PhraseConstraint]
    val pc3 = mock[PhraseConstraint]

    when(pc1.matches(np1, ctx)).thenReturn(true)
    when(pc2.matches(np1, ctx)).thenReturn(false)
    when(pc3.matches(np1, ctx)).thenReturn(true)

    assert(!new ConstraintConjunctionPhrasePattern(pc1 :: pc2 :: pc3 :: Nil).matches(np1, ctx))
    assert(new ConstraintConjunctionPhrasePattern(pc1 :: pc3 :: Nil).matches(np1, ctx))
    assert(new ConstraintConjunctionPhrasePattern(pc3 :: Nil).matches(np1, ctx))
  }

}