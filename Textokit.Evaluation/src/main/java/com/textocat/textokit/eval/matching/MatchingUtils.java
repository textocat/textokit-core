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

import org.apache.uima.cas.Type;

class MatchingUtils {

    public static boolean isCollectionType(Type type) {
        // TODO what about FSList ?
        return type.isArray();
    }

    public static Type getComponentType(Type colType) {
        if (!isCollectionType(colType)) {
            throw new IllegalArgumentException(String.format(
                    "Type %s is not a collection type", colType));
        }
        return colType.getComponentType();
    }

    private MatchingUtils() {
    }
}