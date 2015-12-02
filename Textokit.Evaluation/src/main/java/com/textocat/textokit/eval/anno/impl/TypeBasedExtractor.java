package com.textocat.textokit.eval.anno.impl;

import com.textocat.textokit.eval.ConfigurationKeys;
import com.textocat.textokit.eval.anno.AnnotationExtractor;
import org.apache.uima.cas.*;
import org.apache.uima.cas.text.AnnotationFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.StringUtils.split;

/**
 * @author Rinat Gareev
 */
public class TypeBasedExtractor implements AnnotationExtractor {

    @Autowired
    private TypeSystem typeSystem;

    @Value("${" + ConfigurationKeys.KEY_ANNOTATION_TYPES + "}")
    private String annoTypeNamesString;

    // derived
    private Set<Type> annoTypes;
    private FSMatchConstraint annoMatchConstraint;

    @SuppressWarnings("unused")
    @PostConstruct
    private void init() {
        Set<String> annoTypeNames = newHashSet(split(annoTypeNamesString, " ,;"));
        annoTypes = new HashSet<Type>();
        for (String curTypeName : annoTypeNames) {
            Type curType = typeSystem.getType(curTypeName);
            if (curType == null) {
                throw new IllegalStateException("Can't find type " + curTypeName);
            }
            annoTypes.add(curType);
        }
        // prepare FS constraint
        ConstraintFactory cf = ConstraintFactory.instance();
        FSTypeConstraint typeConstr = cf.createTypeConstraint();
        for (Type curType : annoTypes) {
            typeConstr.add(curType);
        }
        annoMatchConstraint = typeConstr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FSIterator<AnnotationFS> extract(CAS cas) {
        // TODO optimization point - get common ancestor type if any
        FSIterator<AnnotationFS> allAnnoIter = cas.getAnnotationIndex().iterator();
        return cas.createFilteredIterator(allAnnoIter, annoMatchConstraint);
    }

}