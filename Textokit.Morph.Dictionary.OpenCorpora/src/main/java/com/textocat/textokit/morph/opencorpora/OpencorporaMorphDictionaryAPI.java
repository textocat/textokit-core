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
import com.textocat.textokit.commons.util.ManifestUtils;
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPI;
import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.opencorpora.resource.CachedDictionaryDeserializer;
import com.textocat.textokit.morph.opencorpora.resource.ClasspathGramModelResource;
import com.textocat.textokit.morph.opencorpora.resource.ClasspathMorphDictionaryResource;
import com.textocat.textokit.morph.opencorpora.resource.GramModelDeserializer;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;
import org.springframework.core.io.ClassPathResource;

import java.util.List;
import java.util.jar.Manifest;

/**
 * @author Rinat Gareev
 */
public class OpencorporaMorphDictionaryAPI implements MorphDictionaryAPI {

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
        return ExternalResourceFactory.createExternalResourceDescription(ClasspathGramModelResource.class);
    }

    @Override
    public CachedResourceTuple<MorphDictionary> getCachedInstance() throws Exception {
        ClassPathResource cpRes = locateDictionaryClasspathResource();
        CachedDictionaryDeserializer.GetDictionaryResult gdr = CachedDictionaryDeserializer.getInstance().getDictionary(
                cpRes.getURL(), cpRes.getInputStream());
        return new CachedResourceTuple<>(gdr.cacheKey, gdr.dictionary);
    }

    @Override
    public GramModel getGramModel() throws Exception {
        ClassPathResource cpRes = locateDictionaryClasspathResource();
        return GramModelDeserializer.from(cpRes.getInputStream(), cpRes.getURL().toString());
    }

    private static ClassPathResource locateDictionaryClasspathResource() {
        return new ClassPathResource(locateDictionaryClassPath());
    }

    public static String locateDictionaryClassPath() {
        List<Manifest> jarManifests = ManifestUtils.searchByAttributeKey(ME_OPENCORPORA_DICTIONARY_VERSION);
        if (jarManifests.isEmpty())
            throw new IllegalStateException("Can't find an OpenCorpora dictionary in classpath");
        if (jarManifests.size() > 1)
            throw new UnsupportedOperationException("Found several OpenCorpora dictionaries in classpath");
        Manifest ocJarManifest = jarManifests.get(0);
        String version = ocJarManifest.getMainAttributes().getValue(ME_OPENCORPORA_DICTIONARY_VERSION);
        String revision = ocJarManifest.getMainAttributes().getValue(ME_OPENCORPORA_DICTIONARY_REVISION);
        String variant = ocJarManifest.getMainAttributes().getValue(ME_OPENCORPORA_DICTIONARY_VARIANT);
        return String.format(OpencorporaMorphDictionaryAPI.FILENAME_PATTERN_OPENCORPORA_SERIALIZED_DICT,
                version, revision, variant);
    }
}
