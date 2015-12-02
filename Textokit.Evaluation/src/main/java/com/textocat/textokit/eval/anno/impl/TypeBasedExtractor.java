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