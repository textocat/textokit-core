
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

package com.textocat.textokit.io.brat;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class U2BParametersParsingTest {

    @Test
    public void testEntityMappingParsing() {
        EntityDefinitionValue edv = EntityDefinitionValue.fromString(
                "test.Organization");
        assertEquals("test.Organization", edv.uimaTypeName);
        assertEquals(null, edv.bratTypeName);

        edv = EntityDefinitionValue.fromString(
                "test.Organization=>Org");
        assertEquals("test.Organization", edv.uimaTypeName);
        assertEquals("Org", edv.bratTypeName);

        edv = EntityDefinitionValue.fromString(
                "test.Organization =>Org");
        assertEquals("test.Organization", edv.uimaTypeName);
        assertEquals("Org", edv.bratTypeName);

        edv = EntityDefinitionValue.fromString(
                "test.Organization=> Org");
        assertEquals("test.Organization", edv.uimaTypeName);
        assertEquals("Org", edv.bratTypeName);

        edv = EntityDefinitionValue.fromString(
                "test.Organization => Org");
        assertEquals("test.Organization", edv.uimaTypeName);
        assertEquals("Org", edv.bratTypeName);

        edv = EntityDefinitionValue.fromString(
                "test.HL_Organization => HL_Org");
        assertEquals("test.HL_Organization", edv.uimaTypeName);
        assertEquals("HL_Org", edv.bratTypeName);
    }

    @Test
    public void testStructureMappingParsing() {
        StructureDefinitionValue sdv = StructureDefinitionValue.fromString(
                "test.KFU_Merge => K_Merge : firstSlot, secondSlot as KFU_Company ");
        assertEquals("test.KFU_Merge", sdv.uimaTypeName);
        assertEquals("K_Merge", sdv.bratTypeName);
        assertEquals(Lists.newArrayList(
                        new RoleDefinitionValue("firstSlot", null),
                        new RoleDefinitionValue("secondSlot", "KFU_Company")),
                // actual
                sdv.roleDefinitions);

        sdv = StructureDefinitionValue.fromString(
                "test.KFU_Acq : firstSlot, secondSlot as KFU_Company ");
        assertEquals("test.KFU_Acq", sdv.uimaTypeName);
        assertEquals(null, sdv.bratTypeName);
        assertEquals(Lists.newArrayList(
                        new RoleDefinitionValue("firstSlot", null),
                        new RoleDefinitionValue("secondSlot", "KFU_Company")),
                // actual
                sdv.roleDefinitions);
    }

    @Test
    public void testNoteMapperDefinitionParsing() {
        // 1
        NoteMapperDefinitionValue ndv = NoteMapperDefinitionValue.fromString(
                "test.ISSST_Word:ru.ksu.TestNoteMapper");
        assertEquals("test.ISSST_Word", ndv.uimaType);
        assertEquals("ru.ksu.TestNoteMapper", ndv.mapperClassName);

        //2
        ndv = NoteMapperDefinitionValue.fromString(
                "test.ISSST_Word :  ru.ksu.TestNoteMapper");
        assertEquals("test.ISSST_Word", ndv.uimaType);
        assertEquals("ru.ksu.TestNoteMapper", ndv.mapperClassName);

        //3
        ndv = NoteMapperDefinitionValue.fromString(
                "Word :TestNoteMapper");
        assertEquals("Word", ndv.uimaType);
        assertEquals("TestNoteMapper", ndv.mapperClassName);
    }
}