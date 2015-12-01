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

package org.nlplab.brat.ann;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Rinat Gareev
 */
public class AttributeHolder implements HasAttributes {
    private Map<String, Object> attrMap = Maps.newHashMap();

    @Override
    public String getAttribute(String attrName) {
        Object result = attrMap.get(attrName);
        if (result == null) {
            return null;
        }
        if (result instanceof String) {
            return (String) result;
        }
        throw new IllegalArgumentException(String.format(
                "Attribute %s is binary", attrName));
    }

    @Override
    public boolean hasBinaryAttribute(String attrName) {
        Object result = attrMap.get(attrName);
        if (result == null) {
            return false;
        }
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        throw new IllegalArgumentException(String.format(
                "Attribute %s has non-boolean value", attrName));
    }

    @Override
    public Map<String, Object> getAttributesMap() {
        return ImmutableMap.copyOf(attrMap);
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (value == null) {
            attrMap.remove(name);
        } else if (value instanceof Boolean) {
            attrMap.put(name, value);
        } else if (value instanceof String) {
            attrMap.put(name, value);
        } else {
            throw new IllegalArgumentException("Only string and boolean attributes are supported: " + value);
        }
    }
}
