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