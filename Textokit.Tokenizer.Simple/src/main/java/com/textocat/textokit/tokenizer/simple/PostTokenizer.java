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
package com.textocat.textokit.tokenizer.simple;

import com.google.common.collect.*;
import com.textocat.textokit.commons.cas.AnnotationUtils;
import com.textocat.textokit.tokenizer.fstype.*;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Rinat Gareev
 */
public class PostTokenizer extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(PostTokenizer.class);
    }

    // per-CAS state
    private Map<AnnotationFS, Collection<? extends AnnotationFS>> mergedMap;
    private Type wordType;
    private Type numType;

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        mergedMap = Maps.newHashMap();
        wordType = jCas.getCasType(W.type);
        numType = jCas.getCasType(NUM.type);
        try {
            AnnotationIndex<Annotation> tokenBases = jCas.getAnnotationIndex(TokenBase.typeIndexID);
            // sequence of tokens that does not contain whitespace
            List<Token> curTokenSeq = Lists.newLinkedList();
            for (Annotation tokenBase : tokenBases) {
                if (tokenBase instanceof WhiteSpace) {
                    handle(jCas, ImmutableList.copyOf(curTokenSeq));
                    curTokenSeq.clear();
                } else {
                    // it's Token
                    curTokenSeq.add((Token) tokenBase);
                }
            }
            // handle last seq
            handle(jCas, ImmutableList.copyOf(curTokenSeq));
            curTokenSeq.clear();
            // index/unindex
            Set<String> mergedTokenStrings = Sets.newHashSet();
            for (Map.Entry<AnnotationFS, Collection<? extends AnnotationFS>> entry : mergedMap.entrySet()) {
                jCas.addFsToIndexes(entry.getKey());
                mergedTokenStrings.add(entry.getKey().getCoveredText());
                for (AnnotationFS anno : entry.getValue()) {
                    jCas.removeFsFromIndexes(anno);
                }
            }
            getLogger().debug("Merged tokens: " + mergedTokenStrings);
        } finally {
            mergedMap = null;
        }
    }

    private boolean handle(JCas jCas, List<Token> tokens) {
        if (tokens.size() <= 1) {
            return false;
        } else if (tokens.size() == 2) {
            // check abbreviation dictionary
            if (isWord(tokens.get(0)) && isDot(tokens.get(1))
                    && isAbbreviation(getCoveredText(tokens))) {
                makeAnnotation(jCas, tokens.get(0).getType(), tokens);
                return true;
            }
            if (!hasPMOrSpecial(tokens)) {
                makeAnnotation(jCas,
                        isWord(tokens.get(0)) ? tokens.get(0).getType() : wordType,
                        tokens);
            }
        } else if (tokens.size() == 3) {
            Token t0 = tokens.get(0);
            Token t1 = tokens.get(1);
            Token t2 = tokens.get(2);
            if (isPossibleInnerPM(t1) && hasWord(t0, t2)) {
                makeAnnotation(jCas, isWord(t0) ? t0.getType() : wordType, tokens);
                return true;
            }
            // TODO may be RANGE is better as target type, e.g. "12-14"
            if (isNumInternalPM(t1) && isNum(t0) && isNum(t2)) {
                makeAnnotation(jCas, numType, tokens);
            }
        } else {
            // tokens size >= 4
            if (startsLikeUrl(tokens)) {
                makeAnnotation(jCas, URL.class, tokens);
            } else if (isEmail(jCas.getDocumentText().substring(
                    tokens.get(0).getBegin(), tokens.get(tokens.size() - 1).getEnd()))) {
                makeAnnotation(jCas, Email.class, tokens);
            } else {
                // re-run on 'cleaned' internals
                LinkedList<Token> cleaned = Lists.newLinkedList(tokens);
                while (!cleaned.isEmpty() && isPMOrSpecial(cleaned.getFirst())) {
                    cleaned.removeFirst();
                }
                while (!cleaned.isEmpty() && isPMOrSpecial(cleaned.getLast())) {
                    cleaned.removeLast();
                }
                // to avoid infinite recursion
                if (tokens.size() != cleaned.size()) {
                    return handle(jCas, cleaned);
                }
            }
        }
        return false;
    }

    private static final Set<String> abbreviations = ImmutableSet.of("г.");

    // TODO use external dictionary
    private boolean isAbbreviation(String str) {
        return abbreviations.contains(str);
    }

    private static final Set<String> POSSIBLE_INNER_PM = ImmutableSet.of("'", "-", "`");

    private boolean isPossibleInnerPM(Token tkn) {
        return POSSIBLE_INNER_PM.contains(tkn.getCoveredText());
    }

    private boolean isPMOrSpecial(Token tkn) {
        return tkn instanceof PM || tkn instanceof SPECIAL;
    }

    private boolean hasWord(Token... tkns) {
        for (Token tkn : tkns) {
            if (isWord(tkn)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPMOrSpecial(Iterable<Token> tkns) {
        for (Token tkn : tkns) {
            if (isPMOrSpecial(tkn)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    private boolean isHyphen(Token tkn) {
        return "-".equals(tkn.getCoveredText());
    }

    private boolean isDot(Token tkn) {
        return ".".equals(tkn.getCoveredText());
    }

    private boolean isColon(Token t) {
        return ":".equals(t.getCoveredText());
    }

    private boolean isSlash(Token t) {
        return "/".equals(t.getCoveredText());
    }

    private static final Set<String> NUM_INTERNAL_PM = ImmutableSet.of(",", ".", "-");

    private boolean isNumInternalPM(Token tkn) {
        return NUM_INTERNAL_PM.contains((tkn.getCoveredText()));
    }

    private boolean isWord(Token tkn) {
        return tkn instanceof W;
    }

    private boolean isNum(Token tkn) {
        return tkn instanceof NUM;
    }

    private boolean startsLikeUrl(List<Token> tokens) {
        return tokens.size() >= 4 &&
                (isColon(tokens.get(1)) && isSlash(tokens.get(2)) && isSlash(tokens.get(3))
                        && isUrlScheme(tokens.get(0)));
    }

    private final Set<String> URL_SCHEMES = ImmutableSet.of("https", "http", "ftp", "mailto", "file");

    private boolean isUrlScheme(Token t) {
        return URL_SCHEMES.contains(t.getCoveredText());
    }

    private final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile("[^@]+@[-\\p{IsLetter}0-9.]+");

    private boolean isEmail(String s) {
        return SIMPLE_EMAIL_PATTERN.matcher(s).matches();
    }

    private String getCoveredText(Iterable<? extends AnnotationFS> iter) {
        StringBuilder sb = new StringBuilder();
        for (AnnotationFS anno : iter) {
            sb.append(anno.getCoveredText());
        }
        return sb.toString();
    }

    private void makeAnnotation(JCas jCas, Type targetType, List<? extends AnnotationFS> rangeAnnos) {
        int begin = rangeAnnos.get(0).getBegin();
        int end = rangeAnnos.get(rangeAnnos.size() - 1).getEnd();
        mergedMap.put(jCas.getCas().createAnnotation(targetType, begin, end), rangeAnnos);
    }

    private void makeAnnotation(JCas jCas, Class<? extends Annotation> targetType, List<? extends AnnotationFS> rangeAnnos) {
        int begin = rangeAnnos.get(0).getBegin();
        int end = rangeAnnos.get(rangeAnnos.size() - 1).getEnd();
        Annotation resultAnno = AnnotationUtils.create(jCas, begin, end, targetType);
        mergedMap.put(resultAnno, rangeAnnos);
    }
}
