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
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class ConfigPropertiesUtilsTest {

    @Test
    public void test1() {
        Properties props = new Properties();
        props.setProperty("year", "2013");
        props.setProperty("outputDir", "./${baseDir}/output");
        props.setProperty("inputDir", "${baseDir}/input");
        props.setProperty("configDir", "${baseDir}");

        Map<String, String> phValues = Maps.newHashMap();
        phValues.put("baseDir", "current");
        ConfigPropertiesUtils.replacePlaceholders(props, phValues, false);
        assertEquals("2013", props.getProperty("year"));
        assertEquals("./current/output", props.getProperty("outputDir"));
        assertEquals("current/input", props.getProperty("inputDir"));
        assertEquals("current", props.getProperty("configDir"));
    }

}