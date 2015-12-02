package com.textocat.textokit.eval.anno.impl;

import com.google.common.base.Objects;
import com.textocat.textokit.eval.matching.TypeBasedMatcherDispatcher;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rinat Gareev
 */
public class ConfigurableOverlapMatchingStrategy extends OverlapMatchingStrategy {

    @Autowired
    private TypeBasedMatcherDispatcher<AnnotationFS> topMatcher;
    @Autowired
    private TypeSystem ts;

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean match(AnnotationFS goldAnno, AnnotationFS candAnno) {
        return topMatcher.match(goldAnno, candAnno);
    }

    @Override
    protected boolean isCandidate(AnnotationFS goldAnno, AnnotationFS sysAnno) {
        // fast check
        if (Objects.equal(goldAnno.getType(), sysAnno.getType())) {
            return true;
        }
        // else - try to down-cast to types of interest
        for (Type evalType : topMatcher.getRegisteredTypes()) {
            if (ts.subsumes(evalType, goldAnno.getType())
                    && ts.subsumes(evalType, sysAnno.getType())) {
                return true;
            }
        }
        return false;
    }
}