
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

package org.nlplab.brat.ann;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.textocat.textokit.commons.io.IoUtils;
import org.junit.Test;
import org.nlplab.brat.configuration.BratTypesConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class BratAnnotationContainerTest {

    @Test
    public void testIndexedRoleParsing() {
        assertArrayEquals(null, BratAnnotationContainer.parseRoleIndex("someRole"));
        assertArrayEquals(new Object[]{"someRole", 2},
                BratAnnotationContainer.parseRoleIndex("someRole2"));
        assertArrayEquals(new Object[]{"someRole", 30},
                BratAnnotationContainer.parseRoleIndex("someRole030"));
    }

    @Test
    public void testParsingTutorial100Chapter() throws IOException {
        File dir = new File("data/brat-news-tutorial");
        BratTypesConfiguration cfg = BratTypesConfiguration.readFrom(new File(dir, "annotation.conf"));
        BratAnnotationContainer cont = new BratAnnotationContainer(cfg);
        try (Reader annReader = IoUtils.openReader(new File(dir, "101-attribute_annotation.ann"))) {
            cont.readFrom(annReader);
        }
        // check entity with attribute
        BratEntity t1 = (BratEntity) cont.getAnnotation("T1");
        assertEquals("Person", t1.getType().getName());
        assertEquals(497, t1.getBegin());
        assertEquals(505, t1.getEnd());
        assertEquals("Somebody", t1.getSpannedText());
        assertEquals("T1", t1.getId());
        assertEquals(ImmutableMap.<String, Object>builder()
                        .put("Individual", true)
                        .put("Confidence", "High")
                        .build(),
                t1.getAttributesMap());
        // check entity without attribute
        // T5	Organization 584 590	Google
        BratEntity t5 = (BratEntity) cont.getAnnotation("T5");
        assertEquals("Organization", t5.getType().getName());
        assertEquals(584, t5.getBegin());
        assertEquals(590, t5.getEnd());
        assertEquals("Google", t5.getSpannedText());
        assertEquals("T5", t5.getId());
        assertEquals(Maps.newHashMap(), t5.getAttributesMap());
        // check event with attribute
        // TODO
    }
}
