/**
 *
 */
package ru.kfu.itis.cll.uima.util;

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