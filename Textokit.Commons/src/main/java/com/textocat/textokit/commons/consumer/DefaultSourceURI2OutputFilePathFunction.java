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

package com.textocat.textokit.commons.consumer;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.textocat.textokit.commons.DocumentMetadata;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

/**
 * Simply extracts 'path' part from a given source URI.
 * It means that opaque URIs are not supported.
 *
 * @author Rinat Gareev
 */
public class DefaultSourceURI2OutputFilePathFunction implements Function<DocumentMetadata, Path> {

    @Override
    public Path apply(DocumentMetadata metaAnno) {
        String uriStr = metaAnno.getSourceUri();
        if (uriStr == null) {
            return null;
        }
        URI uri = URI.create(uriStr);
        if (uri.isOpaque()) {
            throw new IllegalArgumentException(format("Opaque URIs are not supported: %s", uri));
        }
        String path = uri.getPath();
        Preconditions.checkState(path != null, "URI path is null: " + uri);
        return Paths.get(path);
    }
}
