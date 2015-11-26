
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

package org.nlplab.brat.configuration;

import org.junit.Test;
import org.nlplab.brat.configuration.EventRole.Cardinality;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class BratTypesConfigurationTest {

    @Test
    public void testParsing() throws IOException {
        BratTypesConfiguration btConf = BratTypesConfiguration.readFrom(
                new File("data/test-annotation.conf"));
        btConf.getType("GPE", BratEntityType.class);
        BratEntityType orgType = btConf.getType("Organization", BratEntityType.class);
        BratEventType startOrgType = btConf.getType("Start-org", BratEventType.class);
        assertEquals("Start-org", startOrgType.getName());
        assertEquals(2, startOrgType.getRoles().size());
        assertEquals(Cardinality.NON_EMPTY_ARRAY,
                startOrgType.getRole("Agent-Arg").getCardinality());
        assertEquals(3, startOrgType.getRole("Agent-Arg").getRangeTypes().size());
        assertEquals(Cardinality.ONE, startOrgType.getRole("Org-Arg").getCardinality());
        assertEquals(1, startOrgType.getRole("Org-Arg").getRangeTypes().size());
        assertEquals(orgType, startOrgType.getRole("Org-Arg").getRangeTypes().iterator().next());
        // check empty event parsing
        BratEventType shType = btConf.getType("Something-Happened", BratEventType.class);
        assertEquals("Something-Happened", shType.getName());
        assertEquals(0, shType.getRoles().size());
    }

}
