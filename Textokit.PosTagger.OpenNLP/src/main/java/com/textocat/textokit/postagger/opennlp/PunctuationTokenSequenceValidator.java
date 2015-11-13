/**
 * 
 */
package com.textocat.textokit.postagger.opennlp;

import opennlp.tools.util.SequenceValidator;
import com.textocat.textokit.tokenizer.fstype.NUM;
import com.textocat.textokit.tokenizer.fstype.PM;
import com.textocat.textokit.tokenizer.fstype.SPECIAL;
import com.textocat.textokit.tokenizer.fstype.Token;
import com.textocat.textokit.tokenizer.fstype.W;
import com.textocat.textokit.morph.commons.PunctuationUtils;

/**
 * @author Rinat Gareev
 * 
 */
public class PunctuationTokenSequenceValidator implements SequenceValidator<Token> {

	@Override
	public boolean validSequence(int i, Token[] inputSequence, String[] outcomesSequence,
			String outcome) {
		Token curToken = inputSequence[i];
		return checkForPunctuationTag(curToken, outcome);
	}

	public static boolean checkForPunctuationTag(Token curToken, String curOutcome) {
		boolean isPunctuationTag = PunctuationUtils.isPunctuationTag(curOutcome);
		// TODO
		if (curToken instanceof W || curToken instanceof NUM) {
			return !isPunctuationTag;
		} else if (curToken instanceof PM || curToken instanceof SPECIAL) {
			// curToken is a punctuation or a special symbol
			return isPunctuationTag;
		}
		return true;
	}
}
