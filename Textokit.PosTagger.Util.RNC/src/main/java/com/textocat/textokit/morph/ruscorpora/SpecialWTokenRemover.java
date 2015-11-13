/**
 * 
 */
package com.textocat.textokit.morph.ruscorpora;

import static com.textocat.textokit.commons.cas.AnnotationUtils.toPrettyString;
import static com.textocat.textokit.commons.util.DocumentUtils.getDocumentUri;

import java.util.Map;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import com.textocat.textokit.morph.fs.Word;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;

import com.google.common.collect.Sets;

import com.textocat.textokit.tokenizer.fstype.NUM;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import com.textocat.textokit.postagger.MorphCasUtils;

/**
 * @author Rinat Gareev
 * 
 */
public class SpecialWTokenRemover extends JCasAnnotator_ImplBase {
	
	public static AnalysisEngineDescription createDescription()
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(SpecialWTokenRemover.class);
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		Map<Token, Word> token2WordIndex = MorphCasUtils.getToken2WordIndex(jCas);
		Set<Token> tokens2Remove = Sets.newHashSet();
		for (Token token : JCasUtil.select(jCas, Token.class)) {
			Word word = token2WordIndex.get(token);
			if (word == null && (token instanceof NUM || token instanceof W)) {
				getLogger().warn(String.format(
						"Token %s in %s does not have corresponding Word annotation",
						toPrettyString(token), getDocumentUri(jCas)));
				tokens2Remove.add(token);
			}
		}
		for (Token token : tokens2Remove) {
			token.removeFromIndexes();
		}
	}
}
