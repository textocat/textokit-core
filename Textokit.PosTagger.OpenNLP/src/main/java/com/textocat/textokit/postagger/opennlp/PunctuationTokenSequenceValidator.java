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
