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

/**
 * @author Rinat Gareev
 */
public class ListWhitespaceChars {
    public static void main(String args[]) {
        System.out.println("Code\tWhite\tCategory");
        for (int i = 0x0000; i <= 0xFFFF; i++) {
            if (Character.isWhitespace(i) || isControl(i) || isSep(i)) {
                System.out.println(format(i));
            }
        }
    }

    private static String getCharType(int ch) {
        int chType = Character.getType(ch);
        switch (chType) {
            case Character.SPACE_SEPARATOR:
                return "SPACE_SEPARATOR";
            case Character.LINE_SEPARATOR:
                return "LINE_SEPARATOR";
            case Character.PARAGRAPH_SEPARATOR:
                return "PARAGRAPH_SEPARATOR";
            default:
                return String.valueOf(chType);
        }
    }

    private static boolean isControl(int ch) {
        return Character.CONTROL == Character.getType(ch);
    }

    private static boolean isSep(int ch) {
        switch (Character.getType(ch)) {
            case Character.SPACE_SEPARATOR:
            case Character.LINE_SEPARATOR:
            case Character.PARAGRAPH_SEPARATOR:
                return true;
            default:
                return false;
        }
    }

    private static String format(int codePoint) {
        return String.format("U%04x\t%s\t%s", codePoint,
                Character.isWhitespace(codePoint),
                getCharType(codePoint));
    }
}
