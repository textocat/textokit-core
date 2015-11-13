/**
 *
 */
package com.textocat.textokit.postagger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.textocat.textokit.commons.cas.FSUtils;
import com.textocat.textokit.morph.dictionary.AnnotationAdapterBase;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.morph.model.Lemma;
import com.textocat.textokit.morph.model.Wordform;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;

/**
 * <p/>
 * Uses Wordform.pos to set general lexical category, e.g., NOUN,VERB, etc.
 * <p/>
 * Uses Wordform.grammems to set all grammatical categories, including general
 * one.
 *
 * @author Rinat Gareev
 */
public class DefaultAnnotationAdapter extends AnnotationAdapterBase {

    @Override
    public void apply(JCas jcas, Annotation token, Collection<Wordform> dictWfs) {
        Word word = new Word(jcas);
        word.setBegin(token.getBegin());
        word.setEnd(token.getEnd());
        // TODO check token type
        word.setToken((Token) token);
        List<com.textocat.textokit.morph.fs.Wordform> casWfList = Lists.newLinkedList();
        for (Wordform wf : dictWfs) {
            com.textocat.textokit.morph.fs.Wordform casWf = new com.textocat.textokit.morph.fs.Wordform(jcas);

            BitSet grammems = wf.getGrammems();
            Lemma lemma = dict.getLemma(wf.getLemmaId());
            // set lemma id
            casWf.setLemmaId(lemma.getId());
            // set lemma norm
            casWf.setLemma(lemma.getString());
            // set pos
            casWf.setPos(dict.getGramModel().getPos(lemma.getGrammems()));
            // set grammems
            grammems.or(lemma.getGrammems());
            List<String> gramSet = dict.getGramModel().toGramSet(grammems);
            casWf.setGrammems(FSUtils.toStringArray(jcas, gramSet));

            // set hosting word
            casWf.setWord(word);

            casWfList.add(casWf);
        }
        // set wordforms
        word.setWordforms(FSUtils.toFSArray(jcas, casWfList));

        word.addToIndexes();
    }

    @Override
    public void apply(JCas jcas, Annotation token,
                      Integer lexemeId, final String _lemma, BitSet posBits) {
        Word word = new Word(jcas);
        word.setBegin(token.getBegin());
        word.setEnd(token.getEnd());
        // TODO check token type
        word.setToken((Token) token);

        com.textocat.textokit.morph.fs.Wordform casWf = new com.textocat.textokit.morph.fs.Wordform(jcas);
        String lemma = null;
        if (lexemeId != null) {
            Lemma lex = dict.getLemma(lexemeId);
            lemma = lex.getString();
            casWf.setLemmaId(lexemeId);
        } else if (_lemma != null) {
            lemma = _lemma;
        }
        if (lemma != null) {
            casWf.setLemma(lemma);
        }
        // TODO set 'pos' feature
        // casWf.setPos(...);

        List<String> gramSet = dict.getGramModel().toGramSet(posBits);
        casWf.setGrammems(FSUtils.toStringArray(jcas, gramSet));

        // set hosting word
        casWf.setWord(word);

        // set wordforms
        word.setWordforms(FSUtils.toFSArray(jcas, ImmutableList.of(casWf)));

        word.addToIndexes();
    }
}