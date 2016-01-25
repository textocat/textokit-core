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

package com.textocat.textokit.segmentation.heur;

import com.google.common.collect.Sets;
import com.textocat.textokit.commons.cas.AnnotationOffsetComparator;
import com.textocat.textokit.segmentation.SentenceSplitterAPI;
import com.textocat.textokit.segmentation.fstype.Sentence;
import com.textocat.textokit.tokenizer.fstype.*;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;

import java.util.NavigableSet;

import static com.textocat.textokit.commons.cas.AnnotationUtils.isBefore;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * @author Rinat Gareev
 */
public class SentenceSplitter extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription()
            throws ResourceInitializationException {
        TypeSystemDescription tsDesc = SentenceSplitterAPI.getTypeSystemDescription();
        return createEngineDescription(SentenceSplitter.class, tsDesc);
    }

    private static final Class[] sentenceEndTokenTypes = new Class[]{
            PERIOD.class, EXCLAMATION.class, QUESTION.class, BREAK.class};

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        AnnotationIndex<Token> visibleTokenIdx = jCas.getAnnotationIndex(Token.class);
        if (visibleTokenIdx.size() == 0) {
            return;
        }
        NavigableSet<TokenBase> boundaryCandidates =
                Sets.newTreeSet(AnnotationOffsetComparator.instance(TokenBase.class));
        for (Class<? extends TokenBase> bAnnoType : sentenceEndTokenTypes) {
            boundaryCandidates.addAll(JCasUtil.select(jCas, bAnnoType));
        }
        String txt = jCas.getDocumentText();
        FSIterator<Token> visibleIter = visibleTokenIdx.iterator();
        // get first sentence start
        visibleIter.moveToFirst();
        Token lastSentenceStart = visibleIter.get();

        for (TokenBase boundaryCand : boundaryCandidates) {
            if (isBefore(boundaryCand, lastSentenceStart)) {
                continue;
            }
            Token nextVisToken = getNext(visibleIter, boundaryCand);
            Token prevVisToken = getPrevious(visibleIter, boundaryCand);
            boolean isBoundary = nextVisToken == null;
            if (!isBoundary && boundaryCand instanceof Token) {
                // i.e. candidate is a visible token
                isBoundary = isBreakBetween(txt, boundaryCand, nextVisToken) ||
                        (distanceBetween(boundaryCand, nextVisToken) > 0
                                && !isAbbreviation(prevVisToken)
                                && !isSW(nextVisToken));
            }
            if (!isBoundary && boundaryCand instanceof WhiteSpace) {
                // candidate is a break
                isBoundary = !isSW(nextVisToken);
            }
            if (isBoundary) {
                Token sentEnd;
                if (boundaryCand instanceof Token) {
                    sentEnd = (Token) boundaryCand;
                } else {
                    sentEnd = prevVisToken;
                }
                makeSentence(jCas, lastSentenceStart, sentEnd);
                visibleIter.moveTo(sentEnd);
                visibleIter.moveToNext();
                if (visibleIter.isValid()) {
                    lastSentenceStart = visibleIter.get();
                } else {
                    lastSentenceStart = null;
                    break;
                }
            }
        }
        if (lastSentenceStart != null) {
            visibleIter.moveToLast();
            Token sentEnd = visibleIter.get();
            makeSentence(jCas, lastSentenceStart, sentEnd);
            lastSentenceStart = null;
        }
    }

    private boolean isAbbreviation(Token tok) {
        // TODO elaborate
        return tok.getTypeIndexID() == CW.type && tok.getEnd() - tok.getBegin() == 1;
    }

    private boolean isSW(Token tok) {
        return tok.getTypeIndexID() == SW.type;
    }

    private void makeSentence(JCas cas, Token firstToken, Token lastToken) {
        int begin = firstToken.getBegin();
        int end = lastToken.getEnd();
        if (end <= begin) {
            throw new IllegalStateException(String.format(
                    "Illegal start and end token for sentence: %s, %s",
                    firstToken, lastToken));
        }
        Sentence sentence = new Sentence(cas, begin, end);
        sentence.setFirstToken(firstToken);
        sentence.setLastToken(lastToken);
        sentence.addToIndexes();
    }

    /**
     * Return next element if exists.
     *
     * @param iter   iterator
     * @param anchor an anchor
     * @return next element if exists or null otherwise
     */
    private static Token getNext(FSIterator<Token> iter, TokenBase anchor) {
        iter.moveTo(anchor);
        // now the current fs either greater (for tokens seq it means 'after') or equal to the anchor
        if (iter.isValid()) {
            Token result = iter.get();
            if (result.equals(anchor)) {
                iter.moveToNext();
                if (iter.isValid()) {
                    return iter.get();
                } else {
                    return null;
                }
            } else {
                return result;
            }
        } else {
            return null;
        }
    }

    /**
     * Return previous element if exists.
     *
     * @param iter   iterator
     * @param anchor an anchor
     * @return previous element if exists or null otherwise
     */
    private static Token getPrevious(FSIterator<Token> iter, TokenBase anchor) {
        iter.moveTo(anchor);
        // now the current fs either greater (for tokens seq it means 'after') or equal to the anchor
        if (iter.isValid()) {
            // in any case we should move backward
            iter.moveToPrevious();
            if (iter.isValid()) {
                return iter.get();
            } else {
                return null;
            }
        } else {
            iter.moveToLast();
            if (iter.isValid()) {
                return iter.get();
            } else {
                return null;
            }
        }
    }

    /**
     * @param anno1
     * @param anno2
     * @return 0 if given annotation overlap else return distance between the
     * end of first (in text direction) annotation and the begin of
     * second annotation.
     */
    private static int distanceBetween(AnnotationFS anno1, AnnotationFS anno2) {
        AnnotationFS first;
        AnnotationFS second;
        if (anno1.getBegin() > anno2.getBegin()) {
            first = anno2;
            second = anno1;
        } else if (anno1.getBegin() < anno2.getBegin()) {
            first = anno1;
            second = anno2;
        } else {
            return 0;
        }
        int result = second.getBegin() - first.getEnd();
        return result >= 0 ? result : 0;
    }

    private static boolean isBreakBetween(String txt, Annotation first, Annotation second) {
        for (int i = first.getEnd(); i < second.getBegin(); i++) {
            if (txt.charAt(i) == '\n') {
                return true;
            }
        }
        return false;
    }
}