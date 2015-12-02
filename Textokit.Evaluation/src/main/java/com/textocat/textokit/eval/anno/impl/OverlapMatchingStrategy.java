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

package com.textocat.textokit.eval.anno.impl;

import com.textocat.textokit.commons.cas.AnnotationUtils;
import com.textocat.textokit.commons.cas.OverlapIndex;
import com.textocat.textokit.eval.anno.AnnotationExtractor;
import com.textocat.textokit.eval.anno.MatchingStrategy;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
/*
 * TODO OverlapMatchingStrategy, AnnotationExtractor and TypeBasedMatcherDispatcher 
 * seem to be highly-coupled concepts.
 */
public abstract class OverlapMatchingStrategy implements MatchingStrategy {

    @Autowired
    private AnnotationExtractor annotationExtractor;
    // state fields
    private CAS sysCas;
    private OverlapIndex<AnnotationFS> sysOverlapIdx;

    @Override
    public void changeCas(CAS newSysCas) {
        sysCas = newSysCas;
        // reset state related to previous CAS
        sysOverlapIdx = null;
        if (sysCas != null) {
            sysOverlapIdx = AnnotationUtils.createOverlapIndex(annotationExtractor.extract(sysCas));
        }
    }

    @Override
    public Set<AnnotationFS> searchCandidates(AnnotationFS goldAnno) {
        Set<AnnotationFS> result = sysOverlapIdx.getOverlapping(
                goldAnno.getBegin(),
                goldAnno.getEnd());
        Iterator<AnnotationFS> resultIter = result.iterator();
        while (resultIter.hasNext()) {
            if (!isCandidate(goldAnno, resultIter.next())) {
                resultIter.remove();
            }
        }
        return result;
    }

    @Override
    public AnnotationFS searchExactMatch(AnnotationFS goldAnno, Iterable<AnnotationFS> candidates) {
        for (AnnotationFS candAnno : candidates) {
            if (match(goldAnno, candAnno)) {
                return candAnno;
            }
        }
        return null;
    }

    protected abstract boolean match(AnnotationFS goldAnno, AnnotationFS candAnno);

    protected abstract boolean isCandidate(AnnotationFS goldAnno, AnnotationFS sysAnno);
}