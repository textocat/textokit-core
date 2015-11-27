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


package com.textocat.textokit.commons.cas;

import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;

import java.util.Set;

/**
 * Provides the contract to efficiently deal with search of overlapping
 * annotations. Use
 * {@link AnnotationUtils#createOverlapIndex(java.util.Iterator)} to get default
 * implementation.
 *
 * @author Rinat Gareev
 */
public interface OverlapIndex<A extends AnnotationFS> {

    /**
     * @param begin first character of an annotation
     * @param end   the first character after the annotation
     * @return set of annotations that overlap with annotation whose offsets are
     * given by parameters. Result ordering is defined by offsets
     * (according to {@link AnnotationIndex}. If offsets are equals then
     * source iterator ordering is used.
     * @see AnnotationUtils#overlap(AnnotationFS, AnnotationFS)
     */
    Set<A> getOverlapping(int begin, int end);

}
