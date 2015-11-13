/**
 *
 */
package com.textocat.textokit.postagger.opennlp;

import com.textocat.textokit.morph.commons.PunctuationUtils;
import com.textocat.textokit.tokenizer.fstype.*;
import opennlp.tools.util.SequenceValidator;

/**
 * @author Rinat Gareev
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
