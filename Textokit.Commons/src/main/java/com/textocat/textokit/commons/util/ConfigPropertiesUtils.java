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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.textocat.textokit.commons.io.IoUtils;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rinat Gareev
 * @see IoUtils
 */
public class ConfigPropertiesUtils {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern
            .compile("\\$\\{([\\p{Alnum}._]+)\\}");

    private ConfigPropertiesUtils() {
    }

    public static Properties replacePlaceholders(Properties props,
                                                 Map<String, String> placeholderValues) {
        return replacePlaceholders(props, placeholderValues, false);
    }

    /**
     * Replace ${}-placeholders inside properties values.
     *
     * @param props             target {@link Properties} instance
     * @param placeholderValues
     * @param ignoreAbsent      if false then absent values for some placeholder key will
     *                          raise {@link IllegalStateException}
     * @return given {@link Properties} instance
     */
    public static Properties replacePlaceholders(Properties props,
                                                 Map<String, String> placeholderValues, boolean ignoreAbsent) {
        Map<String, String> replacements = Maps.newHashMap();
        for (String propKey : props.stringPropertyNames()) {
            String propValue = props.getProperty(propKey);
            Matcher phMatcher = PLACEHOLDER_PATTERN.matcher(propValue);
            StringBuffer sb = new StringBuffer(propValue.length());
            while (phMatcher.find()) {
                String replacement = placeholderValues.get(phMatcher.group(1));
                if (replacement == null) {
                    if (ignoreAbsent) {
                        replacement = phMatcher.group();
                    } else {
                        throw new IllegalArgumentException(String.format(
                                "Can't find value for placeholder %s", phMatcher.group()));
                    }
                }
                phMatcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            }
            phMatcher.appendTail(sb);
            String resultValue = sb.toString();
            if (!resultValue.equals(propValue)) {
                replacements.put(propKey, resultValue);
            }
        }
        for (String propKey : replacements.keySet()) {
            props.setProperty(propKey, replacements.get(propKey));
        }
        return props;
    }

    public static String prettyString(Properties props) {
        StringBuilder sb = new StringBuilder();
        for (String key : Sets.newTreeSet(props.stringPropertyNames())) {
            sb.append(key).append("=");
            sb.append(props.getProperty(key));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String getStringProperty(Properties props, String key) {
        return getStringProperty(props, key, true);
    }

    public static String getStringProperty(Properties props, String key, boolean required) {
        String valStr = props.getProperty(key);
        if (valStr == null) {
            if (required)
                throw new IllegalStateException(String.format("No value for '%s'", key));
            else
                return null;
        }
        return valStr;
    }

    public static Integer getIntProperty(Properties props, String key, boolean required) {
        String valStr = props.getProperty(key);
        if (valStr == null) {
            if (required)
                throw new IllegalStateException(String.format("No value for '%s'", key));
            else
                return null;
        }
        try {
            return Integer.valueOf(valStr);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(String.format("Can't parse %s='%s'",
                    key, valStr));
        }
    }

    public static Integer getIntProperty(Properties props, String key) {
        return getIntProperty(props, key, true);
    }

    public static File getFileProperty(Properties props, String key, boolean required) {
        String valStr = props.getProperty(key);
        if (valStr == null) {
            if (required)
                throw new IllegalStateException(String.format("No value for '%s'", key));
            else
                return null;
        }
        return new File(valStr);
    }

}