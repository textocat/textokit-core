/**
 *
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
