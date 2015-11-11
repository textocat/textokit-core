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


package com.textocat.textokit.commons.wfstore;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

import java.util.Map;

/**
 * @author Rinat Gareev
 */
public class DefaultWordformStoreBuilder<TagType> implements WordformStoreBuilder<TagType> {

    // state
    private Map<String, Multiset<TagType>> strKeyMap = Maps.newHashMap();

    @Override
    public void increment(String wordString, TagType tag) {
        Multiset<TagType> tags = strKeyMap.get(wordString);
        if (tags == null) {
            tags = HashMultiset.create();
            strKeyMap.put(wordString, tags);
        }
        tags.add(tag);
    }

    @Override
    public DefaultWordformStore<TagType> build() {
        DefaultWordformStore<TagType> result = new DefaultWordformStore<TagType>();
        result.strKeyMap = Maps.newHashMapWithExpectedSize(strKeyMap.size());
        for (String wf : strKeyMap.keySet()) {
            Multiset<TagType> tagBag = strKeyMap.get(wf);
            int max = 0;
            TagType maxTag = null;
            for (Multiset.Entry<TagType> tagEntry : tagBag.entrySet()) {
                if (tagEntry.getCount() > max) {
                    max = tagEntry.getCount();
                    maxTag = tagEntry.getElement();
                }
            }
            if (maxTag == null) {
                throw new IllegalStateException();
            }
            result.strKeyMap.put(wf, maxTag);
        }
        return result;
    }
}