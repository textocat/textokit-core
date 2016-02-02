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

package com.textocat.textokit.corpus.statistics.dao.units;

import java.net.URI;

public class UnitLocation {

    public URI documentURI;
    public int begin;
    public int end;

    public URI getDocumentURI() {
        return documentURI;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public UnitLocation(URI documentURI, int begin, int end) {
        this.documentURI = documentURI;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + begin;
        result = prime * result
                + ((documentURI == null) ? 0 : documentURI.hashCode());
        result = prime * result + end;
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
        UnitLocation other = (UnitLocation) obj;
        if (begin != other.begin)
            return false;
        if (documentURI == null) {
            if (other.documentURI != null)
                return false;
        } else if (!documentURI.equals(other.documentURI))
            return false;
        if (end != other.end)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d\t%d", documentURI, begin, end);
    }

}