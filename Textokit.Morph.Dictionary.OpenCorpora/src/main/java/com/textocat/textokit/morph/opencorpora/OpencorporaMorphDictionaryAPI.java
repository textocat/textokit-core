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

package com.textocat.textokit.morph.opencorpora;

import com.textocat.textokit.commons.util.CachedResourceTuple;
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPI;
import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.opencorpora.resource.CachedDictionaryDeserializer;
import com.textocat.textokit.morph.opencorpora.resource.ClasspathMorphDictionaryResource;
import com.textocat.textokit.morph.opencorpora.resource.GramModelDeserializer;
import com.textocat.textokit.morph.opencorpora.resource.GramModelResource;
import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Rinat Gareev
 */
public class OpencorporaMorphDictionaryAPI implements MorphDictionaryAPI {
tatu
    public static final String DEFAULT_SERIALIZED_DICT_RELATIVE_PATH = "dict.opcorpora.ser";
    public static final String DEFAULT_SERIALIZED_DICT_RELATIVE_URL =
            "file:" + DEFAULT_SERIALIZED_DICT_RELATIVE_PATH;
    // ME ~ Manifest Entry
    public static final String ME_OPENCORPORA_DICTIONARY_VERSION = "Opencorpora-Dictionary-Version";
    public static final String ME_OPENCORPORA_DICTIONARY_REVISION = "Opencorpora-Dictionary-Revision";
    public static final String ME_OPENCORPORA_DICTIONARY_VARIANT = "Opencorpora-Dictionary-Variant";
    public static final String FILENAME_PATTERN_OPENCORPORA_SERIALIZED_DICT = "opencorpora-%s-%s-%s.dict.ser";

    @Override
    public ExternalResourceDescription getResourceDescriptionForCachedInstance() {
        return ExternalResourceFactory.createExternalResourceDescription(ClasspathMorphDictionaryResource.class);
    }

    @Override
    public ExternalResourceDescription getResourceDescriptionWithPredictorEnabled() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ExternalResourceDescription getGramModelDescription() {
        return ExternalResourceFactory.createExternalResourceDescription(
                GramModelResource.class,
                DEFAULT_SERIALIZED_DICT_RELATIVE_URL);
    }

    @Override
    public CachedResourceTuple<MorphDictionary> getCachedInstance() throws Exception {
        URL serDictUrl = getSerializedDictionaryURL();
        CachedDictionaryDeserializer.GetDictionaryResult gdr = CachedDictionaryDeserializer.getInstance().getDictionary(
                serDictUrl, serDictUrl.openStream());
        return new CachedResourceTuple<MorphDictionary>(gdr.cacheKey, gdr.dictionary);
    }

    @Override
    public GramModel getGramModel() throws Exception {
        URL serDictUrl = getSerializedDictionaryURL();
        return GramModelDeserializer.from(serDictUrl.openStream(), serDictUrl.toString());
    }

    private URL getSerializedDictionaryURL() {
        URL serDictUrl;
        try {
            serDictUrl = UIMAFramework.newDefaultResourceManager()
                    .resolveRelativePath(DEFAULT_SERIALIZED_DICT_RELATIVE_PATH);
        } catch (MalformedURLException e) {
            // should never happen as the URL is hard-coded here
            throw new IllegalStateException(e);
        }
        if (serDictUrl == null) {
            throw new IllegalStateException(String.format("Can't find %s in UIMA datapath",
                    DEFAULT_SERIALIZED_DICT_RELATIVE_PATH));
        }
        return serDictUrl;
    }
}
