package com.textocat.textokit.segmentation.heur;

import com.google.common.collect.Lists;
import com.textocat.textokit.segmentation.SentenceSplitterAPI;
import com.textocat.textokit.segmentation.fstype.Sentence;
import com.textocat.textokit.tokenizer.fstype.BREAK;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.TokenBase;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;

import java.util.Deque;

/**
 * @author Rinat Gareev
 */
public class OneSentencePerLineSplitter extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription() throws ResourceInitializationException {
        TypeSystemDescription tsDesc = SentenceSplitterAPI.getTypeSystemDescription();
        return AnalysisEngineFactory.createEngineDescription(
                OneSentencePerLineSplitter.class, tsDesc);
    }

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        Deque<Token> sentTokens = Lists.newLinkedList();
        for (TokenBase tb : JCasUtil.select(cas, TokenBase.class)) {
            if (tb instanceof BREAK) {
                if (sentTokens.isEmpty()) {
                    continue;
                }
                makeSentence(cas, sentTokens.getFirst(), sentTokens.getLast());
                sentTokens.clear();
            } else if (tb instanceof Token) {
                sentTokens.add((Token) tb);
            }
        }
        // make last sentence if any tokens are left
        if (!sentTokens.isEmpty()) {
            makeSentence(cas, sentTokens.getFirst(), sentTokens.getLast());
        }
    }

    private void makeSentence(JCas cas, Token first, Token last) {
        Sentence sent = new Sentence(cas, first.getBegin(), last.getEnd());
        sent.setFirstToken(first);
        sent.setLastToken(last);
        sent.addToIndexes();
    }
}
