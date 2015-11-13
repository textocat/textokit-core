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

package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.model.Wordform;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;

public class MorphDictionaryImplTest {

    MorphDictionaryImpl dict;
    GramModel gm;

    @Before
    public void setUp() throws Exception {
        FileInputStream fis = FileUtils.openInputStream(
                new File("test-data/dict.opcorpora.test.xml"));
        try {
            dict = XmlDictionaryParser.parse(fis);
            gm = dict.getGramModel();
        } finally {
            IOUtils.closeQuietly(fis);
        }
        dict.setWfPredictor(new DummyWordformPredictor(dict));
    }

    @Test
    public void testGetEntries() {
        assertEquals(1, dict.getEntries("гаджимуратович").size());
        assertEquals(3, dict.getEntries("село").size());
        assertEquals(3, dict.getEntries("а").size());
        assertEquals(4, dict.getEntries("мыркающий").size());
        int prtfGramIdx = gm.getGrammemNumId("PRTF");
        int verbGramIdx = gm.getGrammemNumId("VERB");
        assertTrue(Wordform.getAllGramBits(dict.getEntries("мыркающий").get(0),
                dict).get(prtfGramIdx));
        assertFalse(Wordform.getAllGramBits(
                dict.getEntries("мыркающий").get(0), dict).get(verbGramIdx));
        int mascGramIdx = gm.getGrammemNumId("masc");
        int singGramIdx = gm.getGrammemNumId("sing");
        int femnGramIdx = gm.getGrammemNumId("femn");
        int plurGramIdx = gm.getGrammemNumId("plur");
        assertTrue(dict.getEntries("мыркающий").get(0).getGrammems()
                .get(mascGramIdx));
        assertTrue(dict.getEntries("мыркающий").get(0).getGrammems()
                .get(singGramIdx));
        assertFalse(dict.getEntries("мыркающий").get(0).getGrammems()
                .get(femnGramIdx));
        assertFalse(dict.getEntries("мыркающий").get(0).getGrammems()
                .get(plurGramIdx));
    }

}
