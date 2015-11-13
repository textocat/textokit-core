/**
 *
 */
package com.textocat.textokit.postagger.opennlp;

import com.google.common.collect.Lists;
import com.textocat.textokit.morph.commons.PunctuationUtils;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.morph.fs.Wordform;
import com.textocat.textokit.postagger.MorphCasUtils;
import com.textocat.textokit.tokenizer.fstype.NUM;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import opennlp.model.Event;
import opennlp.tools.util.AbstractEventStream;
import opennlp.tools.util.BeamSearchContextGenerator;
import opennlp.tools.util.ObjectStream;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.textocat.textokit.commons.cas.AnnotationUtils.toPrettyString;
import static com.textocat.textokit.commons.util.DocumentUtils.getDocumentUri;

/**
 * @author Rinat Gareev
 */
public class POSTokenEventStream<ST extends Annotation> extends AbstractEventStream<ST> {

    private BeamSearchContextGenerator<Token> cg;
    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(getClass());

    public POSTokenEventStream(ObjectStream<ST> samples, BeamSearchContextGenerator<Token> cg) {
        super(samples);
        this.cg = cg;
    }

    @Override
    protected Iterator<Event> createEvents(ST spanAnno) {
        Event[] events = generateEvents(spanAnno, cg);
        return Arrays.asList(events).iterator();
    }

    public static Event[] generateEvents(Annotation spanAnno,
                                         BeamSearchContextGenerator<Token> contextGen) {
        JCas jCas;
        try {
            jCas = spanAnno.getCAS().getJCas();
        } catch (CASException e) {
            throw new IllegalStateException(e);
        }
        List<Token> tokens = new ArrayList<Token>(JCasUtil.selectCovered(
                jCas, Token.class, spanAnno));
        Map<Token, Word> token2WordIndex = MorphCasUtils.getToken2WordIndex(jCas, spanAnno);
        List<String> tags = Lists.newArrayListWithExpectedSize(tokens.size());
        for (Token tok : tokens) {
            Word word = token2WordIndex.get(tok);
            String tokStr = tok.getCoveredText();
            if (word == null) {
                if (tok instanceof NUM || tok instanceof W) {
                    throw new IllegalStateException(String.format(
                            "Token %s in %s does not have corresponding Word annotation",
                            toPrettyString(tok), getDocumentUri(jCas)));
                }
                String tag = PunctuationUtils.getPunctuationTag(tokStr);
                tags.add(tag);
            } else {
                Wordform wf = MorphCasUtils.requireOnlyWordform(word);
                String tag = wf.getPos();
                tags.add(String.valueOf(tag));
            }
        }
        return generateEvents(spanAnno, tokens.toArray(new Token[tokens.size()]),
                tags.toArray(new String[tags.size()]),
                contextGen);
    }

    public static Event[] generateEvents(Annotation spanAnno,
                                         Token[] spanTokens, String[] tags,
                                         BeamSearchContextGenerator<Token> cg) {
        Event[] events = new Event[spanTokens.length];

        for (int i = 0; i < spanTokens.length; i++) {

            // it is safe to pass the tags as previous tags because
            // the context generator does not look for non predicted tags
            String[] context = cg.getContext(i, spanTokens, tags,
                    new Object[]{spanAnno});

            events[i] = new Event(tags[i], context);
        }
        return events;
    }
}
