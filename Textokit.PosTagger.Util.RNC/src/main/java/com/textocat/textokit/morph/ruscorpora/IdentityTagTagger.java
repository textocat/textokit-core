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