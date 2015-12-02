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

package com.textocat.textokit.eval.measure;

/**
 * @author Rinat Gareev
 */
public class RecognitionMeasures {

    // state fields
    // false negative
    private float missingCounter = 0;
    // false positive
    private float spuriousCounter = 0;
    // true positive
    private float matchingCounter = 0;

    public void incrementMissing(float delta) {
        missingCounter += delta;
    }

    public void incrementSpurious(float delta) {
        spuriousCounter += delta;
    }

    public void incrementMatching(float delta) {
        matchingCounter += delta;
    }

    public float getPrecision() {
        float recognizedScore = matchingCounter + spuriousCounter;
        return matchingCounter / recognizedScore;
    }

    public float getRecall() {
        return matchingCounter / (matchingCounter + missingCounter);
    }

    public float getF1() {
        float precision = getPrecision();
        float recall = getRecall();
        return 2 * precision * recall / (precision + recall);
    }

    public float getMatchedScore() {
        return matchingCounter;
    }

    public float getSpuriousScore() {
        return spuriousCounter;
    }

    public float getMissedScore() {
        return missingCounter;
    }

    /**
     * @return matchingScore + missingScore
     */
    public float getGoldScore() {
        return matchingCounter + missingCounter;
    }
}