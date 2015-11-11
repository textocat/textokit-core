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


package com.textocat.textokit.commons.io.axml;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
class Annotation {

    private String type;
    private int begin;
    private int end;
    private Map<String, String> featureStringValuesMap;

    Annotation() {
        featureStringValuesMap = Maps.newHashMap();
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFeatureStringValue(String name, String val) {
        featureStringValuesMap.put(name, val);
    }

    public Set<String> getFeatureNames() {
        return Collections.unmodifiableSet(featureStringValuesMap.keySet());
    }

    public String getFeatureStringValue(String name) {
        return featureStringValuesMap.get(name);
    }
}
