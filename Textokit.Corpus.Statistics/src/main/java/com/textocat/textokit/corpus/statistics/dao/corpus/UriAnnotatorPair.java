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

package com.textocat.textokit.corpus.statistics.dao.corpus;

import java.net.URI;

public class UriAnnotatorPair {
    public final URI uri;
    public final String annotatorId;

    public UriAnnotatorPair(URI uri, String annotatorId) {
        this.uri = uri;
        this.annotatorId = annotatorId;
    }

    public URI getUri() {
        return uri;
    }

    public String getAnnotatorId() {
        return annotatorId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((annotatorId == null) ? 0 : annotatorId.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UriAnnotatorPair other = (UriAnnotatorPair) obj;
        if (annotatorId == null) {
            if (other.annotatorId != null)
                return false;
        } else if (!annotatorId.equals(other.annotatorId))
            return false;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        return true;
    }
}