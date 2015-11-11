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

import java.util.Comparator;

/**
 * @author Rinat Gareev
 */
public class Counter<K> {

    private final K key;
    private int value;

    private Counter(K key, int initialValue) {
        this.key = key;
        this.value = initialValue;
    }

    public static <K> Counter<K> create(K key, int initialValue) {
        return new Counter<K>(key, initialValue);
    }

    public static <K> Counter<K> create(K key) {
        return create(key, 0);
    }

    public static <K> Comparator<Counter<K>> valueComparator(Class<? extends K> keyClass) {
        return new Comparator<Counter<K>>() {
            @Override
            public int compare(Counter<K> arg1, Counter<K> arg2) {
                if (arg1.value > arg2.value) {
                    return 1;
                } else if (arg1.value == arg2.value) {
                    return 0;
                } else {
                    return -1;
                }
            }
        };
    }

    public void increment() {
        this.value++;
    }

    public K getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
