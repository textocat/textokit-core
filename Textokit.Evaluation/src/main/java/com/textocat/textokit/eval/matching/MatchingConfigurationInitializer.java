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

package com.textocat.textokit.eval.matching;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.textocat.textokit.commons.cas.FSTypeUtils;
import com.textocat.textokit.eval.ConfigurationKeys;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.springframework.core.env.PropertyResolver;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static com.textocat.textokit.commons.cas.FSTypeUtils.getFeature;
import static org.apache.commons.lang3.StringUtils.split;

/**
 * @author Rinat Gareev
 */
public class MatchingConfigurationInitializer {

    public static final String MATCHER_DELIMITER_CHARS = ",";
    public static final String SUBMATCHER_DELIMITER_CHARS = "&";
    public static final String BOUNDARY_MATCHER = "checkBoundaries";
    public static final String TYPE_MATCHER = "checkType";
    public static final String PREFIX_ATTRIBUTE_MATCHER = "feature.";

    private TypeSystem ts;
    private PropertyResolver propertyResolver;
    private Type uimaAnnotationType;
    // state fields
    private Map<String, CompositeMatcher.Builder<?>> id2Builder = Maps.newHashMap();

    public MatchingConfigurationInitializer(TypeSystem ts, PropertyResolver propertyResolver) {
        this.ts = ts;
        this.propertyResolver = propertyResolver;
        this.uimaAnnotationType = FSTypeUtils.getType(ts, "uima.tcas.Annotation", true);
    }

    public TypeBasedMatcherDispatcher<AnnotationFS> create() {
        String targetTypeNamesStr = propertyResolver
                .getProperty(ConfigurationKeys.KEY_MATCHING_CONFIGURATION_TARGET_TYPE);
        if (targetTypeNamesStr == null) {
            throw new IllegalStateException(String.format(
                    "Can't create matcher because there is no property under key %s",
                    ConfigurationKeys.KEY_MATCHING_CONFIGURATION_TARGET_TYPE));
        }
        TypeBasedMatcherDispatcher.Builder<AnnotationFS> builder =
                TypeBasedMatcherDispatcher.builder(ts);
        List<String> targetTypeNames = Arrays.asList(StringUtils.split(
                targetTypeNamesStr, ",;"));
        for (String ttn : targetTypeNames) {
            Type targetType = FSTypeUtils.getType(ts, ttn, true);
            CompositeMatcher<AnnotationFS> m = createTargetMatcher(targetType);
            builder.addSubmatcher(targetType, m);
        }
        return builder.build();
    }

    public CompositeMatcher<AnnotationFS> createTargetMatcher(Type targetType) {
        String matcherId = targetType.getShortName();
        CompositeMatcher.AnnotationMatcherBuilder builder = (CompositeMatcher.AnnotationMatcherBuilder) getBuilder(
                matcherId,
                targetType);
        return builder.build();
    }

    private <FST extends FeatureStructure> void parseMatchersDescription(
            CompositeMatcher.Builder<FST> builder, List<String> descStrings) {
        for (String matcherStr : descStrings) {
            if (!parseSingleMatcherDescription(builder, matcherStr)) {
                throw new IllegalStateException(String.format(
                        "Can't parse matcher description: '%s'", matcherStr));
            }
        }
    }

    private <FST extends FeatureStructure> boolean parseSingleMatcherDescription(
            CompositeMatcher.Builder<FST> builder, String matcherStr) {
        for (MatcherDescriptionParser curParser : matcherDescParsers) {
            java.util.regex.Matcher regexMatcher = curParser.descRegex.matcher(matcherStr);
            if (regexMatcher.matches()) {
                curParser.onParse(regexMatcher, builder);
                return true;
            }
        }
        return false;
    }

    private CompositeMatcher.Builder<?> getBuilder(String matcherId, Type matcherTargetType) {
        CompositeMatcher.Builder<?> result = id2Builder.get(matcherId);
        if (result != null) {
            return result;
        }
        // get description string
        String matcherDescKey = ConfigurationKeys.PREFIX_MATCHING_CONFIGURATION + matcherId;
        String matchersDesc = propertyResolver.getProperty(matcherDescKey);
        if (matchersDesc == null) {
            throw new IllegalStateException("Can't find matcher descriptions under key "
                    + matcherDescKey);
        }
        // create instance
        result = createBuilder(matcherTargetType);
        id2Builder.put(matcherId, result);
        // parse description string
        String[] matcherStrings = split(matchersDesc, MATCHER_DELIMITER_CHARS);
        parseMatchersDescription(result, Arrays.asList(matcherStrings));
        return result;
    }

    private CompositeMatcher.Builder<?> parseSubMatcher(List<String> desc, Type targetType) {
        String matcherIdReference = getPrefixed("ref:", desc);
        if (matcherIdReference != null) {
            if (desc.size() > 1) {
                throw new IllegalArgumentException(String.format(
                        "Illegal combination of submatchers: %s", desc));
            }
            return getBuilder(matcherIdReference, targetType);
        }
        CompositeMatcher.Builder<?> subMatcherBuilder = createBuilder(targetType);
        parseMatchersDescription(subMatcherBuilder, desc);
        return subMatcherBuilder;
    }

    private abstract class MatcherDescriptionParser {
        private Pattern descRegex;

        protected MatcherDescriptionParser(String descPatternStr) {
            this.descRegex = Pattern.compile(descPatternStr);
        }

        protected abstract <FST extends FeatureStructure> void onParse(
                java.util.regex.Matcher regexMatcher,
                CompositeMatcher.Builder<FST> builder);
    }

    private final List<MatcherDescriptionParser> matcherDescParsers;

    {
        List<MatcherDescriptionParser> matcherDescParsers = Lists.newLinkedList();
        // checkBoundaries
        matcherDescParsers.add(new MatcherDescriptionParser("checkBoundaries") {
            @Override
            protected <FST extends FeatureStructure> void onParse(Matcher regexMatcher,
                                                                  CompositeMatcher.Builder<FST> builder) {
                if (!(builder instanceof CompositeMatcher.AnnotationMatcherBuilder)) {
                    throw new IllegalStateException(
                            "Can't add checkBoundaries matcher for non-annotation FS type");
                }
                ((CompositeMatcher.AnnotationMatcherBuilder) builder).addBoundaryMatcher();
            }
        });
        // checkType
        matcherDescParsers.add(new MatcherDescriptionParser("checkType") {
            @Override
            protected <FST extends FeatureStructure> void onParse(Matcher regexMatcher,
                                                                  CompositeMatcher.Builder<FST> builder) {
                builder.addTypeChecker();
            }
        });
        // attribute matcher
        matcherDescParsers.add(new MatcherDescriptionParser("feature.(\\p{Alnum}+)=\\{([^}]*)\\}") {

            @Override
            protected <FST extends FeatureStructure> void onParse(Matcher regexMatcher,
                                                                  CompositeMatcher.Builder<FST> builder) {
                String featName = regexMatcher.group(1);
                String subMatchersDesc = regexMatcher.group(2);
                List<String> subMatcherStrings = newArrayList(
                        split(subMatchersDesc, SUBMATCHER_DELIMITER_CHARS));

                Feature feature = getFeature(builder.getTargetType(), featName, true);
                Type featRange = feature.getRange();
                if (featRange.isPrimitive()) {
                    if (subMatcherStrings.equals(Arrays.asList("primitive"))) {
                        // handle primitive-ranged feature
                        builder.addPrimitiveFeatureMatcher(featName);
                        return;
                    }
                    throw new IllegalStateException(String.format(
                            "Illegal matcher description for primitive feature %s:\n%s",
                            feature, subMatcherStrings));
                }
                if (MatchingUtils.isCollectionType(featRange)) {
                    // handle array-or-collection-ranged feature
                    Boolean ignoreOrder = null;
                    if (subMatcherStrings.contains("unordered")) {
                        ignoreOrder = true;
                        subMatcherStrings.remove("unordered");
                    }
                    if (subMatcherStrings.contains("ordered")) {
                        ignoreOrder = false;
                        subMatcherStrings.remove("ordered");
                    }
                    Type componentType = MatchingUtils.getComponentType(featRange);
                    if (componentType.isPrimitive()) {
                        subMatcherStrings.remove("primitive");
                        if (!subMatcherStrings.isEmpty()) {
                            throw new IllegalStateException(
                                    String.format(
                                            "Illegal matcher description for primitive collection feature %s:\n%s",
                                            feature, subMatcherStrings));
                        }
                        builder.addPrimitiveCollectionFeatureMatcher(featName, ignoreOrder);
                    } else {
                        CompositeMatcher.Builder<?> elemMatcherBuilder = parseSubMatcher(
                                subMatcherStrings, componentType);
                        builder.addFSCollectionFeatureMatcher(featName, elemMatcherBuilder,
                                ignoreOrder);
                    }
                } else {
                    // handle non-primitive-ranged feature
                    CompositeMatcher.Builder<?> valueMatcherBuilder = parseSubMatcher(
                            subMatcherStrings, featRange);
                    builder.addFSFeatureMatcher(featName, valueMatcherBuilder);
                }
            }
        });
        this.matcherDescParsers = ImmutableList.copyOf(matcherDescParsers);
    }

    /**
     * @param prefix
     * @param src    source collection
     * @return first string from src collection with specified prefix omitted
     */
    private static String getPrefixed(String prefix, Collection<String> src) {
        for (String str : src) {
            if (str.startsWith(prefix)) {
                return str.substring(prefix.length());
            }
        }
        return null;
    }

    private CompositeMatcher.Builder<?> createBuilder(Type type) {
        CompositeMatcher.Builder<?> result;
        if (ts.subsumes(uimaAnnotationType, type)) {
            result = CompositeMatcher.builderForAnnotation(type);
        } else {
            result = CompositeMatcher.builderForFS(type);
        }
        return result;
    }
}