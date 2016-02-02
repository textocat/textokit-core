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

package com.textocat.textokit.corpus.statistics.dao.units;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class InMemoryUnitsDAOTest {
    UnitsDAO dao = new InMemoryUnitsDAO();

    @Before
    public void setUp() throws URISyntaxException {
        dao.addUnitItem(new URI("1"), 0, 10, "1", "one");
        dao.addUnitItem(new URI("1"), 0, 10, "2", "two");
        dao.addUnitItem(new URI("1"), 11, 15, "1", "one");
        dao.addUnitItem(new URI("1"), 11, 15, "2", "one");
        dao.addUnitItem(new URI("2"), 0, 9, "1", "two");
        dao.addUnitItem(new URI("2"), 0, 9, "2", "two");
    }

    private void assertUnits(UnitsDAO dao) throws URISyntaxException {
        Iterable<Unit> units = dao.getUnits();
        assertEquals(3, Iterables.size(units));
        boolean unitWasFounded = false;
        for (Unit unit : units) {
            if (unit.getLocation()
                    .equals(new UnitLocation(new URI("1"), 0, 10))) {
                unitWasFounded = true;
                assertArrayEquals(new String[]{"one", "two"},
                        unit.getSortedClasses());
                ;
            }
        }
        assertTrue(unitWasFounded);
    }

    @Test
    public void testGetUnits() throws URISyntaxException {
        assertUnits(dao);
    }

    private String daoToString() {
        Writer sw = new StringWriter();
        dao.toTSV(sw);
        return sw.toString();
    }

    @Test
    public void testToTSV() {
        List<String> tsvLines = Lists.newArrayList(daoToString().split(
                "[\\r\\n]+"));
        assertEquals(6, tsvLines.size());
        assertTrue(tsvLines.contains("1\t11\t15\t1\tone"));
    }

    @Test
    public void testAddUnitsFromTSV() throws URISyntaxException, IOException {
        UnitsDAO daoFromTSV = new InMemoryUnitsDAO();
        daoFromTSV.addUnitsFromTSV(new StringReader(daoToString()));
        assertUnits(daoFromTSV);
    }

}
