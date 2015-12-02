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

package com.textocat.textokit.eval.event;

import com.textocat.textokit.eval.measure.RecognitionMeasures;
import org.apache.uima.cas.text.AnnotationFS;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

/**
 * @author Rinat Gareev
 */
public class OverlapHelperTest {

    @Test
    public void notOverlappingMustFail() {
        try {
            OverlapHelper.evaluateOverlap(anno(0, 10), anno(10, 15), new RecognitionMeasures());
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("IllegalArgumentException expected");
    }

    @Test
    public void sameBordersGive1() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(5, 10), anno(5, 10), rm);
        assertEquals(1, rm.getMatchedScore(), 0.001d);
        assertEquals(0, rm.getMissedScore(), 0.001d);
        assertEquals(0, rm.getSpuriousScore(), 0.001d);
    }

    @Test
    public void goldWiderBefore() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(5, 10), anno(7, 10), rm);
        assertEquals(0.6, rm.getMatchedScore(), 0.001d);
        assertEquals(0.4, rm.getMissedScore(), 0.001d);
        assertEquals(0, rm.getSpuriousScore(), 0.001d);
    }

    @Test
    public void goldWiderAfter() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(5, 15), anno(5, 10), rm);
        assertEquals(0.5, rm.getMatchedScore(), 0.001d);
        assertEquals(0.5, rm.getMissedScore(), 0.001d);
        assertEquals(0, rm.getSpuriousScore(), 0.001d);
    }

    @Test
    public void goldBefore() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(5, 15), anno(10, 20), rm);
        assertEquals(0.5, rm.getMatchedScore(), 0.001d);
        assertEquals(0.5, rm.getMissedScore(), 0.001d);
        assertEquals(0.5, rm.getSpuriousScore(), 0.001d);
    }

    @Test
    public void goldAfter() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(5, 15), anno(0, 10), rm);
        assertEquals(0.5, rm.getMatchedScore(), 0.001d);
        assertEquals(0.5, rm.getMissedScore(), 0.001d);
        assertEquals(0.5, rm.getSpuriousScore(), 0.001d);
    }

    @Test
    public void goldWider() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(0, 20), anno(10, 15), rm);
        assertEquals(0.25, rm.getMatchedScore(), 0.001d);
        assertEquals(0.75, rm.getMissedScore(), 0.001d);
        assertEquals(0, rm.getSpuriousScore(), 0.001d);
    }

    @Test
    public void goldInside() {
        RecognitionMeasures rm = new RecognitionMeasures();
        OverlapHelper.evaluateOverlap(anno(5, 15), anno(0, 20), rm);
        assertEquals(1, rm.getMatchedScore(), 0.001d);
        assertEquals(0, rm.getMissedScore(), 0.001d);
        assertEquals(1, rm.getSpuriousScore(), 0.001d);
    }

    private AnnotationFS anno(int begin, int end) {
        AnnotationFS result = mock(AnnotationFS.class);
        stub(result.getBegin()).toReturn(begin);
        stub(result.getEnd()).toReturn(end);
        return result;
    }
}