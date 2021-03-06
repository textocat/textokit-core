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

package com.textocat.textokit.eval.event;

import com.textocat.textokit.eval.measure.RecognitionMeasures;
import org.apache.uima.cas.text.AnnotationFS;

/**
 * @author Rinat Gareev
 */
public class OverlapHelper {

    public static void evaluateOverlap(AnnotationFS gold, AnnotationFS sys,
                                       RecognitionMeasures measures) {
        if (gold.getEnd() <= sys.getBegin() || sys.getEnd() <= gold.getBegin()) {
            throw new IllegalArgumentException(String.format(
                    "Annotations %s and %s do not overlap!", gold, sys));
        }
        // sanity check - one gold anno must give 1 score in total to the counters
        float goldBefore = measures.getGoldScore();

        // legend for schemas below: s - spurious, ! - matched, m - missing
        // Example 1.
        // golden ................sss|!!!!!!!.mmmm|.........
        // system ...............|...........|..............
        // Example 2.
        // golden ................sss|!!!!!!!|ssss..........
        // system ...............|................|.........
        float goldLength = gold.getEnd() - gold.getBegin();

        int deltaBefore = sys.getBegin() - gold.getBegin();
        if (deltaBefore > 0) {
            measures.incrementMissing(deltaBefore / goldLength);
        } else {
            measures.incrementSpurious(-deltaBefore / goldLength);
        }

        int deltaAfter = sys.getEnd() - gold.getEnd();
        if (deltaAfter > 0) {
            measures.incrementSpurious(deltaAfter / goldLength);
        } else {
            measures.incrementMissing(-deltaAfter / goldLength);
        }

        float overlapLength = Math.min(sys.getEnd(), gold.getEnd())
                - Math.max(sys.getBegin(), gold.getBegin());
        // sanity check
        if (overlapLength <= 0) {
            throw new IllegalStateException("Overlap length = " + overlapLength);
        }
        measures.incrementMatching(overlapLength / goldLength);

        // sanity check
        float goldAfter = measures.getGoldScore();
        if (goldAfter - goldBefore - 1 > 0.01f) {
            throw new IllegalStateException("Sanity check failed: goldAfter - goldBefore = "
                    + (goldAfter - goldBefore));
        }
    }

    private OverlapHelper() {
    }
}