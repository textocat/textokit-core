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

import com.google.common.collect.Lists;
import opennlp.tools.postag.POSContextGenerator;

import java.util.ArrayList;

/**
 * @author Rinat Gareev
 */
public class ACPOSContextGenerator implements POSContextGenerator {

    private final int prevTagsInHistory;

    public ACPOSContextGenerator() {
        this(2);
    }

    public ACPOSContextGenerator(int prevTagsInHistory) {
        this.prevTagsInHistory = prevTagsInHistory;
    }

    @Override
    public String[] getContext(int pos, String[] tokens, String[] prevTags, Object[] ac) {
        ArrayList<String> result = Lists.newArrayListWithExpectedSize(
                ac == null ? 0 : ac.length + 2);
        for (Object acVal : ac) {
            if (acVal instanceof String) {
                result.add((String) acVal);
            }
        }
        ContextGeneratorUtils.addPreviousTags(pos, prevTags, prevTagsInHistory, result);
        return result.toArray(new String[result.size()]);
    }
}
