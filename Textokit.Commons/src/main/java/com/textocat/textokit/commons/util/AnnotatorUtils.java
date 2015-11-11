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


package com.textocat.textokit.commons.util;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.Arrays;

/**
 * @author Rinat Gareev
 */
public class AnnotatorUtils {

    private AnnotatorUtils() {
    }

    public static void mandatoryParam(String paramName, Object value)
            throws ResourceInitializationException {
        if (value == null) {
            throw new ResourceInitializationException(
                    new IllegalStateException(String.format(
                            "Missing mandatory parameter '%s' value", paramName)));
        }
    }

    public static void requireParam(boolean expr, String paramName, Object value)
            throws ResourceInitializationException {
        if (!expr) {
            throw new ResourceInitializationException(
                    new IllegalStateException(String.format(
                            "Illegal value of parameter '%s': %s",
                            paramName, value)));
        }
    }

    public static void requireParams(boolean expr, String[] paramNames, Object[] paramValues)
            throws ResourceInitializationException {
        if (!expr) {
            throw new ResourceInitializationException(
                    new IllegalStateException(String.format(
                            "Illegal value of parameters '%s': %s",
                            Arrays.toString(paramNames), Arrays.toString(paramValues))));
        }
    }

    public static void mandatoryResourceObject(String resKey, Object resource)
            throws ResourceInitializationException {
        if (resource == null) {
            throw new ResourceInitializationException(
                    new IllegalStateException(String.format(
                            "Missing mandatory resource under '%s' key", resKey)));
        }
    }

    public static void annotationTypeExist(String typeName, Type type)
            throws AnalysisEngineProcessException {
        if (type == null) {
            throw new AnalysisEngineProcessException(
                    new IllegalStateException(String.format(
                            "Unknown type - '%s'", typeName)));
        }
    }

    public static Feature featureExist(Type type, String featureName)
            throws AnalysisEngineProcessException {
        Feature result = type.getFeatureByBaseName(featureName);
        if (result == null) {
            throw new AnalysisEngineProcessException(
                    new IllegalStateException(String.format(
                            "Type %s doesn't have feature '%s'", type, featureName)));
        }
        return result;
    }

}