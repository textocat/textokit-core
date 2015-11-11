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

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class DefaultCorpusSplit implements CorpusSplit {
    private Set<String> trainingSetPaths;
    private Set<String> testingSetPaths;

    public DefaultCorpusSplit(Iterable<String> trainingSetPaths, Iterable<String> testingSetPaths) {
        this.trainingSetPaths = ImmutableSet.copyOf(trainingSetPaths);
        this.testingSetPaths = ImmutableSet.copyOf(testingSetPaths);
    }

    @Override
    public Set<String> getTrainingSetPaths() {
        return trainingSetPaths;
    }

    @Override
    public Set<String> getTestingSetPaths() {
        return testingSetPaths;
    }
}