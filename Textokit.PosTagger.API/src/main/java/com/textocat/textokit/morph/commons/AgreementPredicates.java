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

package com.textocat.textokit.morph.commons;

import com.google.common.collect.ImmutableMap;
import com.textocat.textokit.morph.commons.AgreementPredicate.Builder;
import com.textocat.textokit.morph.dictionary.resource.GramModel;

import static com.textocat.textokit.morph.model.MorphConstants.*;

/**
 * @author Rinat Gareev
 */
public class AgreementPredicates {

    public static ImmutableMap<String, TwoTagPredicate> numberGenderCaseCombinations(
            GramModel gramModel) {
        AgreementPredicate numAgr = numberAgreement(gramModel);
        AgreementPredicate gndrAgr = genderAgreement(gramModel);
        AgreementPredicate caseAgr = caseAgreement(gramModel);
        return ImmutableMap.<String, TwoTagPredicate>builder()
                .put("NumberAgr", numAgr)
                .put("GenderAgr", gndrAgr)
                .put("CaseAgr", caseAgr)
                .put("NumberGenderAgr", TwoTagPredicateConjunction.and(numAgr, gndrAgr))
                .put("NumberCaseAgr", TwoTagPredicateConjunction.and(numAgr, caseAgr))
                .put("GenderCaseAgr", TwoTagPredicateConjunction.and(gndrAgr, caseAgr))
                .put("NumberGenderCaseAgr", TwoTagPredicateConjunction.and(numAgr, gndrAgr, caseAgr))
                .build();
    }

    public static AgreementPredicate caseAgreement(GramModel gm) {
        return caseAgrBuilder.build(gm);
    }

    public static AgreementPredicate numberAgreement(GramModel gm) {
        return numberAgrBuilder.build(gm);
    }

    public static AgreementPredicate genderAgreement(GramModel gm) {
        return genderAgrBuilder.build(gm);
    }

    private static final Builder caseAgrBuilder = new Builder()
            .agree(nomn)
            .agree(gent).agree(gent, gen1).agree(gent, gen2).agree(gen1).agree(gen2)
            .agree(accs).agree(nomn, acc2).agree(acc2)
            .agree(datv)
            .agree(ablt)
            .agree(loct).agree(loct, loc1).agree(loct, loc2).agree(loc1).agree(loc2)
            .agree(voct);

    private static final Builder numberAgrBuilder = new Builder()
            .agree(sing)
            .agree(plur);

    private static final Builder genderAgrBuilder = new Builder()
            .agree(masc).agree(masc, GNdr)
            .agree(femn).agree(femn, GNdr)
            .agree(neut).agree(neut, GNdr)
            .agree(GNdr);

    private AgreementPredicates() {
    }
}
