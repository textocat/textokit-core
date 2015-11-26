
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

package com.textocat.textokit.io.brat;

import org.apache.uima.cas.*;
import org.apache.uima.fit.util.FSCollectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Package private utils.
 *
 * @author Rinat Gareev
 */
class PUtils {

    /**
     * @param feat
     * @return true if the range type of the given feature allows multiple
     * values
     */
    public static boolean hasCollectionRange(Feature feat) {
        // TODO handle lists
        return feat.getRange().isArray();
    }

    public static Type getCollectionElementType(Feature targetFeat) {
        // TODO handle lists
        if (targetFeat.getRange().isArray()) {
            return targetFeat.getRange().getComponentType();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static FeatureStructure toCompatibleCollection(CAS cas, Feature targetFeat,
                                                          Collection<? extends FeatureStructure> srcCol) {
        if (srcCol == null) {
            return null;
        }
        // TODO handle lists
        if (targetFeat.getRange().isArray()) {
            ArrayFS result = cas.createArrayFS(srcCol.size());
            int i = 0;
            for (FeatureStructure elemFS : srcCol) {
                result.set(i, elemFS);
                i++;
            }
            return result;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static List<FeatureStructure> toList(Feature targetFeat, FeatureStructure fs) {
        if (fs == null) {
            return null;
        }
        // TODO handle lists
        if (targetFeat.getRange().isArray()) {
            return new ArrayList<FeatureStructure>(
                    FSCollectionFactory.create((ArrayFS) fs));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static final String toProperJavaName(final String s) {
        StringBuilder sb = new StringBuilder(s);
        // hyphen index
        int hIndex = sb.indexOf("-");
        while (hIndex >= 0) {
            sb.deleteCharAt(hIndex);
            if (hIndex < sb.length()) {
                sb.replace(hIndex, hIndex + 1, sb.substring(hIndex, hIndex + 1).toUpperCase());
            }
            hIndex = sb.indexOf("-");
        }
        return sb.toString();
    }

    private PUtils() {
    }
}
