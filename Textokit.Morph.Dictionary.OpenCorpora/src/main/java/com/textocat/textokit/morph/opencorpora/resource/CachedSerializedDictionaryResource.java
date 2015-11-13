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

package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.GramModelHolder;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder;
import com.textocat.textokit.morph.opencorpora.resource.CachedDictionaryDeserializer.GetDictionaryResult;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import java.net.URL;

/**
 * {@link MorphDictionaryHolder} that implements a memory cache to hold
 * references to deserialized {@link MorphDictionary} instances. Cache key is a
 * UIMA data resource URL.
 * <p>
 * Use it with caution! Its primary idea is to avoid heavy memory-leaks when
 * several UIMA-based pipelines run sequentially. The main reason is strong
 * reference map to loggers within {@link UIMAFramework_impl} class while each
 * UIMA logger hold reference to a {@link ResourceManager} instance.
 * </p>
 *
 * @author Rinat Gareev
 */
public class CachedSerializedDictionaryResource implements MorphDictionaryHolder, GramModelHolder,
        SharedResourceObject {

    // state fields
    @SuppressWarnings("unused")
    private CacheResourceKey cacheKey;
    private MorphDictionary dict;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(DataResource dr) throws ResourceInitializationException {
        if (this.dict != null) {
            throw new IllegalStateException(
                    "Repeated SharedResourceObjectInvocation#load invocation");
        }
        URL resUrl = dr.getUrl();
        try {
            CachedDictionaryDeserializer deser = CachedDictionaryDeserializer.getInstance();
            GetDictionaryResult deserResult = deser.getDictionary(resUrl, dr.getInputStream());
            this.cacheKey = deserResult.cacheKey;
            this.dict = deserResult.dictionary;
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public MorphDictionary getDictionary() {
        return dict;
    }

    @Override
    public GramModel getGramModel() {
        return dict.getGramModel();
    }

}