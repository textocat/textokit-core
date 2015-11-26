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
import static org.junit.Assert.fail;

/**
 * @author Rinat Gareev
 */
public class AnnotationUtilsTest {
    public static final Table<Offsets, Offsets, Boolean> overlapTestTable = HashBasedTable.create();

    static {
        overlapTestTable.put(new Offsets(10, 50), new Offsets(50, 60), false);
        overlapTestTable.put(new Offsets(10, 51), new Offsets(50, 60), true);
        overlapTestTable.put(new Offsets(20, 30), new Offsets(10, 40), true);
        overlapTestTable.put(new Offsets(10, 49), new Offsets(50, 60), false);
        overlapTestTable.put(new Offsets(10, 50), new Offsets(10, 50), true);
        overlapTestTable.put(new Offsets(10, 50), new Offsets(10, 51), true);
        overlapTestTable.put(new Offsets(10, 50), new Offsets(11, 50), true);
        // nothing overlaps with empty offsets
        overlapTestTable.put(new Offsets(10, 10), new Offsets(20, 30), false);
        overlapTestTable.put(new Offsets(10, 10), new Offsets(5, 15), false);
        overlapTestTable.put(new Offsets(10, 10), new Offsets(10, 15), false);
        overlapTestTable.put(new Offsets(10, 10), new Offsets(5, 10), false);
        // this table must be simmetrical
        Table<Offsets, Offsets, Boolean> tmp = HashBasedTable.create();
        for (Table.Cell<Offsets, Offsets, Boolean> cell : overlapTestTable.cellSet()) {
            tmp.put(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
        }
        overlapTestTable.putAll(tmp);
        if (overlapTestTable.size() != 22) {
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
        for (Table.Cell<Offsets, Offsets, Boolean> testCell : overlapTestTable.cellSet()) {
            firstAnno.setBegin(testCell.getRowKey().getBegin());
            firstAnno.setEnd(testCell.getRowKey().getEnd());
            //
            secondAnno.setBegin(testCell.getColumnKey().getBegin());
            secondAnno.setEnd(testCell.getColumnKey().getEnd());
            //
            if (testCell.getValue()) {
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
}
