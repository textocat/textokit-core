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

import com.textocat.textokit.commons.util.ManifestUtils;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder;
import com.textocat.textokit.morph.opencorpora.OpencorporaMorphDictionaryAPI;
import org.apache.uima.fit.component.Resource_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import static com.textocat.textokit.morph.opencorpora.OpencorporaMorphDictionaryAPI.*;

/**
 * @author Rinat Gareev
 */
public class ClasspathMorphDictionaryResource extends Resource_ImplBase implements MorphDictionaryHolder {

    private CacheResourceKey cacheKey;
    private MorphDictionary dict;

    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map<String, Object> aAdditionalParams) throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams))
            return false;
        List<Manifest> jarManifests = ManifestUtils.searchByAttributeKey(ME_OPENCORPORA_DICTIONARY_VERSION);
        if (jarManifests.isEmpty())
            throw new IllegalStateException("Can't find OpenCorpora dictionary in classpath");
        if (jarManifests.size() > 1)
            throw new UnsupportedOperationException("Found several OpenCorpora dictionaries in classpath");
        Manifest ocJarManifest = jarManifests.get(0);
        String version = ocJarManifest.getMainAttributes().getValue(ME_OPENCORPORA_DICTIONARY_VERSION);
        String revision = ocJarManifest.getMainAttributes().getValue(ME_OPENCORPORA_DICTIONARY_REVISION);
        String variant = ocJarManifest.getMainAttributes().getValue(ME_OPENCORPORA_DICTIONARY_VARIANT);
        String path = String.format(OpencorporaMorphDictionaryAPI.FILENAME_PATTERN_OPENCORPORA_SERIALIZED_DICT,
                version, revision, variant);
        ClassPathResource resource = new ClassPathResource(path);
        try {
            try (InputStream resourceIS = resource.getInputStream()) {
                CachedDictionaryDeserializer.GetDictionaryResult getDictResult =
                        CachedDictionaryDeserializer.getInstance().getDictionary(resource.getURL(), resourceIS);
                this.cacheKey = getDictResult.cacheKey;
                this.dict = getDictResult.dictionary;
            }
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
        return true;
    }

    @Override
    public MorphDictionary getDictionary() {
        return dict;
    }
}
