/**
 *
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
