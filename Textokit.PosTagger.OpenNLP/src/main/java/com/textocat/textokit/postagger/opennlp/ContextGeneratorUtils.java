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

import java.util.Collection;

/**
 * @author Rinat Gareev
 */
public class ContextGeneratorUtils {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static void addPreviousTags(int index, String[] prevTags, int prevTagsToAdd,
                                       Collection<String> targetCol) {
        if (prevTags == null) {
            prevTags = EMPTY_STRING_ARRAY;
        }
        // sanity check - prev tags must be defined at least till index-1
        if (index - 1 >= prevTags.length) {
            throw new IllegalStateException();
        }
        for (int pt = 1; pt <= prevTagsToAdd; pt++) {
            int t = index - pt;
            if (t < 0) {
                break;
            }
            String val = new StringBuilder("pt").append(pt)
                    .append('=')
                    .append(prevTags[t])
                    .toString();
            targetCol.add(val);
        }
    }

    public static String getPreviousTag(int index, String[] prevTags) {
        if (prevTags == null) {
            return null;
        }
        // sanity check - prev tags must be defined at least till index-1
        if (index - 1 >= prevTags.length) {
            throw new IllegalStateException();
        }
        int prevTagIndex = index - 1;
        if (prevTagIndex >= 0) {
            return prevTags[prevTagIndex];
        } else {
            return null;
        }
    }

    private ContextGeneratorUtils() {
    }
}
