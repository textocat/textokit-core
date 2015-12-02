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

package com.textocat.textokit.eval.anno;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;

import java.util.Set;

/**
 * @author Rinat Gareev
 */
public interface MatchingStrategy {

    void changeCas(CAS newSysCas);

    Set<AnnotationFS> searchCandidates(AnnotationFS goldAnno);

    /**
     * @param godlAnno
     * @param candidates
     * @return return the first candidate that exactly matches the given
     * goldAnno
     */
    AnnotationFS searchExactMatch(AnnotationFS goldAnno, Iterable<AnnotationFS> candidates);

}