package com.textocat.textokit.eval;

import com.google.common.collect.ComparisonChain;
import org.apache.uima.cas.text.AnnotationFS;

import java.util.Comparator;

/**
 * @author Rinat Gareev
 * @deprecated is not used anymore because evaluated annotation structure can
 * have richer structure than just borders and type
 */
@Deprecated
class AnnotationOffsetComparator implements Comparator<AnnotationFS> {

    public static final AnnotationOffsetComparator INSTANCE = new AnnotationOffsetComparator();

    @Override
    public int compare(AnnotationFS first, AnnotationFS second) {
        if (first == second) {
            return 0;
        }
        return ComparisonChain.start()
                .compare(first.getBegin(), second.getBegin())
                .compare(second.getEnd(), first.getEnd())
                .compare(first.getType().getName(), second.getType().getName())
                .result();
    }
}