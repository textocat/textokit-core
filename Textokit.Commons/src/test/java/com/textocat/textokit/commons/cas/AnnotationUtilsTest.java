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

package com.textocat.textokit.commons.cas;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.textocat.textokit.commons.util.Offsets;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Rinat Gareev
 */
public class AnnotationUtilsTest {
    // value is a number of shared characters
    public static final Table<Offsets, Offsets, Integer> overlapTestTable = HashBasedTable.create();

    static {
        overlapTestTable.put(new Offsets(10, 50), new Offsets(50, 60), 0);
        overlapTestTable.put(new Offsets(10, 51), new Offsets(50, 60), 1);
        overlapTestTable.put(new Offsets(20, 30), new Offsets(10, 40), 10);
        overlapTestTable.put(new Offsets(10, 49), new Offsets(50, 60), 0);
        overlapTestTable.put(new Offsets(10, 50), new Offsets(10, 50), 40);
        overlapTestTable.put(new Offsets(10, 50), new Offsets(10, 51), 40);
        overlapTestTable.put(new Offsets(10, 50), new Offsets(11, 50), 39);
        // nothing overlaps with empty offsets
        overlapTestTable.put(new Offsets(10, 10), new Offsets(20, 30), 0);
        overlapTestTable.put(new Offsets(10, 10), new Offsets(5, 15), 0);
        overlapTestTable.put(new Offsets(10, 10), new Offsets(10, 15), 0);
        overlapTestTable.put(new Offsets(10, 10), new Offsets(5, 10), 0);
        // this table must be simmetrical
        Table<Offsets, Offsets, Integer> tmp = HashBasedTable.create();
        for (Table.Cell<Offsets, Offsets, Integer> cell : overlapTestTable.cellSet()) {
            tmp.put(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
        }
        overlapTestTable.putAll(tmp);
        if (overlapTestTable.size() != 21) {
            throw new IllegalStateException();
        }
    }

    @Test
    public void testOverlap() throws ResourceInitializationException, CASException {
        JCas cas = CasCreationUtils.createCas(
                TypeSystemDescriptionFactory.createTypeSystemDescription(), null, null)
                .getJCas();
        Annotation firstAnno = new Annotation(cas);
        Annotation secondAnno = new Annotation(cas);
        for (Table.Cell<Offsets, Offsets, Integer> testCell : overlapTestTable.cellSet()) {
            firstAnno.setBegin(testCell.getRowKey().getBegin());
            firstAnno.setEnd(testCell.getRowKey().getEnd());
            //
            secondAnno.setBegin(testCell.getColumnKey().getBegin());
            secondAnno.setEnd(testCell.getColumnKey().getEnd());
            //
            if (testCell.getValue() > 0) {
                if (!AnnotationUtils.overlap(firstAnno, secondAnno)) {
                    fail(format("Annotations %s and %s must overlap but they do not", firstAnno, secondAnno));
                }
            } else {
                if (AnnotationUtils.overlap(firstAnno, secondAnno)) {
                    fail(format("Annotations %s and %s must not overlap but they do", firstAnno, secondAnno));
                }
            }
        }
    }

    @Test
    public void testOverlapLength() throws ResourceInitializationException, CASException {
        JCas cas = CasCreationUtils.createCas(
                TypeSystemDescriptionFactory.createTypeSystemDescription(), null, null)
                .getJCas();
        Annotation firstAnno = new Annotation(cas);
        Annotation secondAnno = new Annotation(cas);
        for (Table.Cell<Offsets, Offsets, Integer> testCell : overlapTestTable.cellSet()) {
            firstAnno.setBegin(testCell.getRowKey().getBegin());
            firstAnno.setEnd(testCell.getRowKey().getEnd());
            //
            secondAnno.setBegin(testCell.getColumnKey().getBegin());
            secondAnno.setEnd(testCell.getColumnKey().getEnd());
            //
            assertEquals("overlapLength",
                    AnnotationUtils.overlapLength(firstAnno, secondAnno),
                    testCell.getValue().longValue());
        }
    }
}
