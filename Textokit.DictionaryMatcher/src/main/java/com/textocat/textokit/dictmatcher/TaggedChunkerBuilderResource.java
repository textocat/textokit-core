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

package com.textocat.textokit.dictmatcher;

import com.google.common.base.Splitter;
import com.textocat.textokit.chunk.Chunker;
import com.textocat.textokit.chunk.ChunkerBuilder;
import com.textocat.textokit.resource.SpringResourceLocator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Rinat Gareev
 */
public class TaggedChunkerBuilderResource extends SpringResourceLocator {

    public static final String PARAM_CHUNKER_BUILDER_CLASS = "chunkerBuilderClass";

    public static ExternalResourceDescription createDescription(String resourceLocation) {
        return createDescription(resourceLocation, "com.textocat.textokit.dictmatcher.mensa.MensaChunkerBuilder");
    }

    public static ExternalResourceDescription createDescription(
            String resourceLocation, Class<? extends ChunkerBuilder> builderClass) {
        return createDescription(resourceLocation, builderClass.getName());
    }

    public static ExternalResourceDescription createDescription(String resourceLocation, String builderClassName) {
        return ExternalResourceFactory.createExternalResourceDescription(TaggedChunkerBuilderResource.class,
                PARAM_CHUNKER_BUILDER_CLASS, builderClassName,
                PARAM_RESOURCE_LOCATION, resourceLocation);
    }

    @ConfigurationParameter(name = PARAM_CHUNKER_BUILDER_CLASS)
    private Class<? extends ChunkerBuilder> chunkerBuilderClass;
    // state fields
    private Chunker<String> chunker;

    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map<String, Object> aAdditionalParams) throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams)) return false;
        //
        ChunkerBuilder<String> builder;
        try {
            //noinspection unchecked
            builder = chunkerBuilderClass.newInstance();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
        //
        try (InputStream in = resourceMeta.getInputStream()) {
            LineIterator lineIterator = IOUtils.lineIterator(in, "UTF-8");
            while (lineIterator.hasNext()) {
                DictEntry de = parseLine(lineIterator.nextLine());
                if (de != null)
                    builder.addEntry(de.tokens, de.tag);
            }
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
        chunker = builder.build();
        return true;
    }

    private DictEntry parseLine(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return null;
        }
        if (line.startsWith("#")) {
            return null;
        }
        String[] tokTagSplit = line.split(TAG_DELIMITER);
        if (tokTagSplit.length != 2) cantParseLine(line);
        String tag = tokTagSplit[1].trim();
        Iterable<String> tokens = TOKEN_SPLITTER.split(tokTagSplit[0]);
        return new DictEntry(tokens, tag);
    }

    public static final String TOKEN_SEPARATOR = " ";
    public static final String TAG_DELIMITER = "\t";
    private static Splitter TOKEN_SPLITTER = Splitter.on(TOKEN_SEPARATOR).trimResults().omitEmptyStrings();

    private void cantParseLine(String line) {
        throw new IllegalStateException("Can't parse line:\n" + line);
    }

    @Override
    public Object getResource() {
        return chunker;
    }

    private class DictEntry {
        private final Iterable<String> tokens;
        private final String tag;

        public DictEntry(Iterable<String> tokens, String tag) {
            this.tokens = tokens;
            this.tag = tag;
        }
    }
}
