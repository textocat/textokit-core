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
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
class Annotation {

    private String id;
    private String type;
    private int begin;
    private int end;
    // map feature name => String (primitive feat val), Annotation (FS val) or List<Annotation> (FSArray val)
    private Map<String, Object> featureValuesMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    Annotation() {
        featureValuesMap = Maps.newHashMap();
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
        featureValuesMap.put(name, val);
    }

    public void setFeatureFSValue(String name, Annotation val) {
        featureValuesMap.put(name, val);
    }

    public Annotation getFeatureFSValue(String name) {
        return getFeatureValue(name, Annotation.class);
    }

    public void setFeatureFSArrayValue(String name, List<Annotation> val) {
        featureValuesMap.put(name, val);
    }

    @SuppressWarnings("unchecked")
    public List<Annotation> getFeatureFSArrayValue(String name) {
        return getFeatureValue(name, List.class);
    }

    /**
     * @return feature names which has been set a value
     */
    public Set<String> getFeatureNames() {
        return Collections.unmodifiableSet(featureValuesMap.keySet());
    }

    public String getFeatureStringValue(String name) {
        return getFeatureValue(name, String.class);
    }

    private <V> V getFeatureValue(String name, Class<V> expectedValClass) {
        Object res = featureValuesMap.get(name);
        if (res == null) return null;
        if (expectedValClass.isInstance(res)) {
            return expectedValClass.cast(res);
        } else {
            throw new IllegalStateException(String.format(
                    "Feature %s has non-%s value: %s", name, expectedValClass.getName(), res));
        }
    }

    // note for equals impl -- already USED as a key in Tables
}
