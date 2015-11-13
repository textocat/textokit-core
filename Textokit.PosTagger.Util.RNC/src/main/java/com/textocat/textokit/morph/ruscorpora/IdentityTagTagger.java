/**
 *
 */
package com.textocat.textokit.morph.ruscorpora;

import com.google.common.collect.Sets;
import com.textocat.textokit.commons.cas.FSUtils;
import com.textocat.textokit.morph.fs.Wordform;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;

import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class IdentityTagTagger implements RusCorporaTagMapper {

    @Override
    public void mapFromRusCorpora(RusCorporaWordform srcWf, Wordform targetWf) {
        JCas jCas;
        try {
            jCas = targetWf.getCAS().getJCas();
        } catch (CASException e) {
            throw new RuntimeException(e);
        }
        targetWf.setPos(srcWf.getPos());
        targetWf.setLemma(srcWf.getLex());
        Set<String> resultGrams = Sets.newLinkedHashSet();
        resultGrams.addAll(srcWf.getLexGrammems());
        resultGrams.addAll(srcWf.getWordformGrammems());
        if (!resultGrams.isEmpty()) {
            targetWf.setGrammems(FSUtils.toStringArray(jCas, resultGrams));
        }
    }

}