
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

package com.textocat.textokit.io.brat;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.textocat.textokit.commons.cas.FSTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.initializable.Initializable;
import org.apache.uima.resource.ResourceInitializationException;
import org.nlplab.brat.configuration.BratAttributeType;
import org.nlplab.brat.configuration.BratEntityType;
import org.nlplab.brat.configuration.BratEventType;
import org.nlplab.brat.configuration.BratRelationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.textocat.textokit.commons.cas.FSTypeUtils.getAnnotationType;
import static com.textocat.textokit.io.brat.PUtils.toProperJavaName;
import static java.util.Arrays.asList;

/**
 * @author Rinat Gareev
 */
public class AutoBratUimaMappingFactory extends BratUimaMappingFactoryBase implements Initializable {

    public static final String PARAM_NAMESPACES_TO_SCAN = "namespacesToScan";
    public static final String PARAM_NAME_REWRITES_KEYS = "bratNameRewriteKeys";
    public static final String PARAM_NAME_REWRITES_VALUES = "bratNameRewriteValues";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ConfigurationParameter(name = PARAM_NAMESPACES_TO_SCAN, mandatory = false)
    private Set<String> namespacesToScan;
    @ConfigurationParameter(name = PARAM_NAME_REWRITES_KEYS, mandatory = false)
    private List<String> bratNameRewriteKeys;
    @ConfigurationParameter(name = PARAM_NAME_REWRITES_VALUES, mandatory = false)
    private List<String> bratNameRewriteValues;
    //
    private Type annotationType;
    private Map<String, String> bratNameRewrites;

    @Override
    public void initialize(UimaContext ctx) throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(this, ctx);
        bratNameRewrites = Maps.newHashMap();
        if (bratNameRewriteKeys != null) {
            if (bratNameRewriteKeys.size() != bratNameRewriteValues.size()) {
                throw new IllegalStateException(String.format(
                        "%s size must correspond to %s",
                        PARAM_NAME_REWRITES_VALUES, PARAM_NAME_REWRITES_KEYS));
            }
            for (int i = 0; i < bratNameRewriteKeys.size(); i++) {
                bratNameRewrites.put(bratNameRewriteKeys.get(i), bratNameRewriteValues.get(i));
            }
        }
    }

    @Override
    public void setTypeSystem(TypeSystem ts) {
        super.setTypeSystem(ts);
        annotationType = getAnnotationType(ts);
    }

    @Override
    public BratUimaMapping getMapping() throws ResourceInitializationException {
        BratUimaMapping.Builder b = BratUimaMapping.builder();
        // map entity types
        for (BratEntityType bratType : bratTypesCfg.getEntityTypes()) {
            String bratName = bratType.getName();
            Type uimaType = searchTypeByBratName(bratName);
            if (uimaType == null) {
                log.warn("Brat entity type {} will be ignored", bratName);
            } else {
                // search matching attributes
                Map<String, Feature> attr2FeatMap = Maps.newHashMap();
                for (BratAttributeType attrType : bratTypesCfg.getAttributes()) {
                    Feature f = searchFeatureByBratName(uimaType, attrType.getName());
                    if (f != null) {
                        log.info("Brat attribute {} for type {} is mapped into UIMA feature {}",
                                attrType.getName(), bratName, f.getName());
                        attr2FeatMap.put(attrType.getName(), f);
                    }
                }
                b.addEntityMapping(bratType, uimaType, attr2FeatMap, null);
                log.info("Brat entity type {} is mapped to UIMA type {}", bratName,
                        uimaType);
            }
        }
        // map relation types
        for (BratRelationType bratType : bratTypesCfg.getRelationTypes()) {
            String bratName = bratType.getName();
            Type uimaType = searchTypeByBratName(bratName);
            if (uimaType == null) {
                log.warn("Brat relation type {} will be ignored", bratName);
            } else {
                Map<Feature, String> featureRoles = Maps.newHashMap();
                for (String argName : asList(bratType.getArg1Name(), bratType.getArg2Name())) {
                    Feature feat = searchFeatureByBratRoleName(uimaType, argName);
                    if (feat == null) {
                        log.warn("There is no feature {} in {}", argName, uimaType);
                        break;
                    } else {
                        featureRoles.put(feat, argName);
                        log.info("Argument {}#{} is mapped to feature {}", bratName, argName, feat);
                    }
                }
                if (featureRoles.size() == 2) {
                    b.addRelationMapping(bratType, uimaType, featureRoles);
                    log.info("Brat relation type {} is mapped to UIMA type {}", bratName,
                            uimaType);
                } else {
                    log.warn("Brat relation type {} will be ignored. Look at previous warnings",
                            bratName);
                }
            }
        }
        // map event types
        for (BratEventType bratType : bratTypesCfg.getEventTypes()) {
            String bratName = bratType.getName();
            Type uimaType = searchTypeByBratName(bratName);
            if (uimaType == null) {
                log.warn("Brat event type {} will be ignored", bratName);
            } else {
                Map<Feature, String> featureRoles = Maps.newHashMap();
                for (String roleName : bratType.getRoles().keySet()) {
                    Feature feat = searchFeatureByBratRoleName(uimaType, roleName);
                    if (feat == null) {
                        log.warn("Role {}#{} will be ignored", bratName, roleName);
                    } else {
                        featureRoles.put(feat, roleName);
                        log.info("Role {}#{} is mapped to feature {}", bratName, roleName, feat);
                    }
                }
                b.addEventMapping(bratType, uimaType, featureRoles);
                log.info("Brat event type {} is mapped to UIMA type {}", bratName,
                        uimaType);
            }
        }
        return b.build();
    }

    private String rewriteOrSelf(String origName) {
        if (bratNameRewrites.containsKey(origName)) {
            return bratNameRewrites.get(origName);
        }
        return origName;
    }

    private Feature searchFeatureByBratRoleName(Type uimaType, String roleName) {
        String featName = roleName;
        Feature feat = uimaType.getFeatureByBaseName(featName);
        if (feat != null) {
            return feat;
        }
        featName = toProperJavaName(featName);
        feat = uimaType.getFeatureByBaseName(featName);
        if (feat != null) {
            return feat;
        }
        if (featName.length() > 0 && Character.isUpperCase(featName.charAt(0))) {
            featName = StringUtils.uncapitalize(featName);
            feat = uimaType.getFeatureByBaseName(featName);
            if (feat != null) {
                return feat;
            }
        }
        return null;
    }

    private Feature searchFeatureByBratName(Type uimaType, String bratName) {
        bratName = rewriteOrSelf(bratName);
        Set<String> baseNameCandidates = Sets.newLinkedHashSet();
        baseNameCandidates.add(bratName);
        baseNameCandidates.add(StringUtils.uncapitalize(toProperJavaName(bratName)));
        for (String baseName : baseNameCandidates) {
            Feature f = uimaType.getFeatureByBaseName(baseName);
            if (f != null) {
                return f;
            }
        }
        return null;
    }

    private Type searchTypeByBratName(String bratName) {
        bratName = rewriteOrSelf(bratName);
        // generate candidate base names
        Set<String> baseNameCandidates = Sets.newLinkedHashSet();
        // add original name
        baseNameCandidates.add(bratName);
        // capitalize characters after hyphens and remove hyphens
        baseNameCandidates.add(toProperJavaName(bratName));
        // search
        for (String baseName : baseNameCandidates) {
            Type t = searchTypeByBaseName(baseName);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    private Type searchTypeByBaseName(String baseName) {
        @SuppressWarnings("unchecked")
        Iterator<Type> iter = Iterators.filter(ts.getTypeIterator(), Predicates.and(asList(
                annotationTypePredicate,
                typeBaseNamePredicate(baseName),
                namespacePredicate)));
        Type result = null;
        if (iter.hasNext()) {
            result = iter.next();
            if (iter.hasNext()) {
                throw new IllegalStateException(String.format(
                        "There are at least two types with target basename: %s, %s",
                        result, iter.next()));
            }
        }
        return result;
    }

    private Predicate<Type> typeBaseNamePredicate(final String baseName) {
        return new Predicate<Type>() {
            @Override
            public boolean apply(Type t) {
                return baseName.equals(t.getShortName());
            }
        };
    }

    private Predicate<Type> annotationTypePredicate = new Predicate<Type>() {
        @Override
        public boolean apply(Type t) {
            return ts.subsumes(annotationType, t);
        }
    };

    /*
     * returns true if namespacesToScan parameter is empty
     * OR a type belongs to some namespace from namespacesToScan
     */
    private Predicate<Type> namespacePredicate = new Predicate<Type>() {
        @Override
        public boolean apply(Type t) {
            if (namespacesToScan == null || namespacesToScan.isEmpty()) {
                return true;
            }
            LinkedHashSet<String> tNss = FSTypeUtils.getNamespaces(t);
            return !Sets.intersection(namespacesToScan, tNss).isEmpty();
        }
    };
}
