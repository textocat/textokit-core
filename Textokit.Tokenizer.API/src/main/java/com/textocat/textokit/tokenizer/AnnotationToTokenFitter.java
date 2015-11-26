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

/**
 *
 */
package com.textocat.textokit.tokenizer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.textocat.textokit.commons.cas.OverlapIndex;
import com.textocat.textokit.commons.util.Offsets;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static com.textocat.textokit.commons.cas.AnnotationUtils.createOverlapIndex;
import static com.textocat.textokit.commons.cas.AnnotationUtils.toPrettyString;
import static java.lang.String.format;

/**
 * TODO test
 *
 * @author Rinat Gareev
 */
public class AnnotationToTokenFitter<ST extends Annotation> extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription(
            Class<? extends Annotation> targetTypeClass) throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(AnnotationToTokenFitter.class,
                PARAM_TARGET_TYPE, targetTypeClass.getName());
    }

    public static final String PARAM_TARGET_TYPE = "targetType";

    @ConfigurationParameter(name = PARAM_TARGET_TYPE, mandatory = true)
    private Class<ST> targetTypeClass;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        Set<ST> anno2Remove = Sets.newLinkedHashSet();
        Map<ST, Offsets> anno2Fix = Maps.newLinkedHashMap();
        OverlapIndex<Token> tokenOverlapIndex =
                createOverlapIndex(JCasUtil.select(jCas, Token.class).iterator());
        //
        for (ST span : JCasUtil.select(jCas, targetTypeClass)) {
            Offsets spanOffsets = new Offsets(span);
            LinkedList<Token> overlappingTokens = Lists.newLinkedList(
                    tokenOverlapIndex.getOverlapping(spanOffsets.getBegin(), spanOffsets.getEnd()));
            if (overlappingTokens.isEmpty()) {
                getLogger().warn(format("No overlapping tokens for typed span: %s", toPrettyString(span)));
                anno2Remove.add(span);
                continue;
            }
            // OT stands for 'overlapping token'
            Token firstOT = overlappingTokens.getFirst();
            Token lastOT = overlappingTokens.getLast();
            // check the first overlapping token
            if (firstOT.getBegin() != spanOffsets.getBegin()) {
                spanOffsets = new Offsets(firstOT.getBegin(), spanOffsets.getEnd());
            }
            // check the last overlapping token
            if (lastOT.getEnd() != spanOffsets.getEnd()) {
                spanOffsets = new Offsets(spanOffsets.getBegin(), lastOT.getEnd());
            }
            //
            if (!spanOffsets.isIdenticalWith(span)) {
                // fix
                anno2Fix.put(span, spanOffsets);
            }
        }
        // remove empty spans
        for (ST span : anno2Remove) {
            span.removeFromIndexes();
        }
        // fix span offsets
        for (Map.Entry<ST, Offsets> e : anno2Fix.entrySet()) {
            ST span = e.getKey();
            String oldText = span.getCoveredText();
            Offsets fixedOffsets = e.getValue();
            span.removeFromIndexes();
            span.setBegin(fixedOffsets.getBegin());
            span.setEnd(fixedOffsets.getEnd());
            span.addToIndexes();
            String newText = span.getCoveredText();
            getLogger().debug(format(
                    "Annotation offsets are fixed: '%s' => '%s'",
                    oldText, newText));
        }
    }
}
