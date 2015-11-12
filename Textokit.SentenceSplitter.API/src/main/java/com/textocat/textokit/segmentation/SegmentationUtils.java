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

package com.textocat.textokit.segmentation;

import com.google.common.collect.ImmutableSet;
import com.textocat.textokit.tokenizer.fstype.Token;
import org.apache.uima.cas.text.AnnotationFS;

import java.util.Set;

import static com.textocat.textokit.tokenizer.TokenUtils.getTokenAfter;
import static com.textocat.textokit.tokenizer.TokenUtils.getTokenBefore;

public class SegmentationUtils {

    private static final Set<String> undirectedQMs = ImmutableSet.of(
            "\"", "'", "\u201A", "\u201B", "\u201E", "\u201F", "\uFF02", "\uFF07");
    private static final Set<String> leftQMs = ImmutableSet.of(
            "«", "\u2018", "\u201C", "\u2039");
    private static final Set<String> rightQMs = ImmutableSet.of(
            "»", "\u2019", "\u201D", "\u203A");

    public static boolean isLeftQuoted(Token token) {
        Token tokenBefore = getTokenBefore(token);
        return tokenBefore != null && isLeftQM(tokenBefore);
    }

    public static boolean isRightQuoted(Token token) {
        Token tokenAfter = getTokenAfter(token);
        return tokenAfter != null && isRightQM(tokenAfter);
    }

    public static boolean isLeftQM(AnnotationFS anno) {
        String chars = anno.getCoveredText();
        return undirectedQMs.contains(chars) || leftQMs.contains(chars);
    }

    public static boolean isRightQM(AnnotationFS anno) {
        String chars = anno.getCoveredText();
        return undirectedQMs.contains(chars) || rightQMs.contains(chars);
    }

    private SegmentationUtils() {
    }

    public static void main(String[] args) {
        System.out.println("Undirected:\n" + undirectedQMs);
        System.out.println("Left:\n" + leftQMs);
        System.out.println("Right:\n" + rightQMs);
    }
}