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

import com.textocat.textokit.morph.model.{MorphConstants => M}
import com.textocat.textokit.phrrecog.cas.Phrase
import com.textocat.textokit.shaltef.util.{CasTestUtils, NprCasBuilder}
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

/**
 * @author Rinat Gareev
 */
class PhraseConstraintTestSuite extends FunSuite with MockitoSugar with CasTestUtils {

  private val caseIds = Set(M.nomn, M.gent, M.datv, M.accs, M.ablt, M.loct)
  private val text = "foobar foobar foobar супротив foobar foobar"

  test("Constraints with headGrammeme target") {
    val cb = new NprCasBuilder(text, Nil)
    import cb._

    val pc1 = BinOpPhraseConstraint(
      HeadGrammemeTarget(caseIds),
      Equals,
      new ConstantScalarValue("accs"))
    val trigger = w("trigger", 0, 1).addGrammems("datv")

    val positivePhrase = {
      w("head1", 2, 3).addGrammems("accs")
      w("mod1", 4, 5)
      np("head1", depWordIds = "mod1" :: Nil, index = true)
    }
    assert(pc1.matches(positivePhrase, MatchingContext(trigger)),
      "%s does not match %s".format(pc1, positivePhrase))

    val negativePhrase = {
      w("head2", 6, 7).addGrammems("datv", "311")
      np("head2", index = true)
    }
    assert(!pc1.matches(negativePhrase, MatchingContext(trigger)),
      "%s matches %s".format(pc1, negativePhrase))

    // test with trigger ref value
    val pc2 = BinOpPhraseConstraint(
      HeadGrammemeTarget(caseIds),
      Equals,
      new TriggerGrammemeReference(caseIds))

    assert(!pc2.matches(positivePhrase, MatchingContext(trigger)),
      "%s matches %s".format(pc2, positivePhrase))

    assert(pc2.matches(negativePhrase, MatchingContext(trigger)),
      "%s does not match %s".format(pc2, negativePhrase))
  }

  test("Constraints with preposition target") {
    val cb = new NprCasBuilder(text, Nil)
    import cb._

    val pc1 = BinOpPhraseConstraint(
      PrepositionTarget,
      Equals,
      new ConstantScalarValue("супротив"))

    val trigger = w("trig", 7, 8)

    val positivePhrase = {
      w("head1", 2, 3).addGrammems("gent")
      w("mod1", 4, 5)
      // супротив
      w(21, 29)
      np("head1", prepId = "супротив", depWordIds = "mod1" :: Nil, index = true)
    }

    val negativePhrase = {
      w("head2", 12, 13).addGrammems("gent")
      w("к", 10, 11)
      np("head2", prepId = "к", index = true)
    }

    assert(pc1.matches(positivePhrase, MatchingContext(trigger)),
      "%s does not match %s".format(pc1, positivePhrase))
    assert(!pc1.matches(negativePhrase, MatchingContext(trigger)),
      "%s matches %s".format(pc1, negativePhrase))
  }

  test("Constraints with headPath op") {
    val text = "30 марта 2006 года с космодрома Байконур в Казахстане стартует в космос экипаж 13-й долговременной экспедиции Международной космической станции. Викиновости событий и происшествий."
    val cb = new NprCasBuilder(text, Nil)
    import cb._

    val trigger = w(54, 62)
    val np1 = {
      w(19, 20)
      w(21, 31)
      w(32, 40)
      np("космодрома", "с", depWordIds = "Байконур" :: Nil, index = true)
    }
    val np2 = {
      w(72, 78)
      w(79, 83)
      w(84, 98)
      w(99, 109)
      w(110, 123)
      w(124, 135)
      w(136, 143)
      val ssNP = np("станции", depWordIds = "Международной" :: "космической" :: Nil)
      val sNP = np("экспедиции", depWordIds = "13-й" :: "долговременной" :: Nil, depNPs = ssNP :: Nil)
      np("экипаж", depNPs = sNP :: Nil, index = true)
    }

    val np3 = {
      w(145, 156)
      w(157, 164)
      w(165, 166)
      w(167, 179)
      val snp1 = np("событий")
      val snp2 = np("происшествий")
      np("Викиновости", depNPs = snp1 :: snp2 :: Nil, index = true)
    }

    val pc1 = UnOpPhraseConstraint(
      HasHeadsPath,
      new ConstantCollectionValue("космодрома" :: Nil))

    val pc2 = UnOpPhraseConstraint(
      HasHeadsPath,
      new ConstantCollectionValue("космодрома" :: "действия" :: Nil))

    val pc3 = UnOpPhraseConstraint(
      HasHeadsPath,
      new ConstantCollectionValue("экипаж" :: Nil))

    val pc4 = UnOpPhraseConstraint(
      HasHeadsPath,
      new ConstantCollectionAlternatives(Set("команда" :: Nil, "экипаж" :: "экспедиции" :: Nil)))

    val pc5 = UnOpPhraseConstraint(
      HasHeadsPath,
      new ConstantCollectionAlternatives(Set("экипаж" :: "экспедиции" :: "станции" :: Nil)))

    val ctx = MatchingContext(trigger)

    assertMatch(pc1, np1, ctx)
    assertNotMatch(pc2, np1, ctx)
    assertNotMatch(pc3, np1, ctx)
    assertNotMatch(pc4, np1, ctx)
    assertNotMatch(pc5, np1, ctx)

    assertNotMatch(pc1, np2, ctx)
    assertNotMatch(pc2, np2, ctx)
    assertMatch(pc3, np2, ctx)
    assertMatch(pc4, np2, ctx)
    assertMatch(pc5, np2, ctx)

    val pc6 = UnOpPhraseConstraint(
      HasHeadsPath,
      new ConstantCollectionAlternatives(Set("Викиновости" :: "происшествий" :: Nil)))
    assertMatch(pc6, np3, ctx)
  }

  private def assertMatch(pc: PhraseConstraint, phr: Phrase, ctx: MatchingContext) {
    assert(pc.matches(phr, ctx), "%s does not match %s".format(pc, phr))
  }

  private def assertNotMatch(pc: PhraseConstraint, phr: Phrase, ctx: MatchingContext) {
    assert(!pc.matches(phr, ctx), "%s matches %s".format(pc, phr))
  }
}