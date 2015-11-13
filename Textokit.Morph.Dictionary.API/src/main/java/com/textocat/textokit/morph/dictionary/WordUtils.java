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

package com.textocat.textokit.morph.dictionary;

import java.lang.Character.UnicodeBlock;
import java.util.regex.Pattern;

/**
 * @author Rinat Gareev
 */
// TODO name properly & move to morph.commons package
public class WordUtils {

    public static boolean isRussianWord(String token) {
        if (token.isEmpty()) {
            return false;
        }
        Character lastLetter = null;
        // find last letter
        for (int i = token.length() - 1; i >= 0; i--) {
            char ch = token.charAt(i);
            if (Character.isLetter(ch)) {
                lastLetter = ch;
                break;
            }
        }
        if (lastLetter == null) {
            return false;
        }
        // check is it cyrillic
        return UnicodeBlock.of(lastLetter).equals(UnicodeBlock.CYRILLIC);
    }

    public static String normalizeToDictionaryForm(String str) {
        str = unicodeMarksPattern.matcher(str).replaceAll("");
        str = str.trim().toLowerCase();
        // str = StringUtils.replaceChars(str, "ёЁ", "еЕ");
        return str;
    }

    private static Pattern unicodeMarksPattern = Pattern.compile("[\\p{Mc}\\p{Me}\\p{Mn}]");

    private WordUtils() {
    }
}