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


package com.textocat.textokit.commons.io.axml;

import com.google.common.collect.Lists;
import com.textocat.textokit.commons.cas.FSUtils;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;
import org.xml.sax.SAXException;
import test.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.textocat.textokit.commons.cas.AnnotationUtils.coveredTextFunction;
import static com.textocat.textokit.commons.cas.AnnotationUtils.findByCoveredText;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class AXMLReaderTest {

    private final TypeSystemDescription inputTSD = createTypeSystemDescription("test.entities-ts");

    @Test
    public void readTest1Xml() throws ResourceInitializationException, IOException, SAXException, CASException {
        CAS cas = CasCreationUtils.createCas(inputTSD, null, null);
        JCas jCas = cas.getJCas();
        AXMLReader.read(new File("test-data/test1.xml"), cas);
        String expectedText = readFileToString(new File("test-data/test1.txt"), "utf-8");
        expectedText = expectedText.replaceAll("\r\n", "\n");
        assertEquals(expectedText, cas.getDocumentText());
        List<String> expectedPersons = asList(
                "Представители молодого поколения сицилийской мафии",
                "Современные «авторитеты» Cosa Nostra",
                "молодые мафиози",
                "мафиози из Палермо Доменико Палацотто",
                "он", "он",
                "Палацотто",
                "Представители органов правопорядка",
                "итальянских преступников",
                "Они",
                "членов мафии",
                "своих рядах",
                "воспитанной на соцсетях молодежи",
                "Ее члены"
        );
        List<String> expectedPerMentionTypes = asList(
                "nominal",
                null,
                null,
                null,
                "pronoun", "pronoun",
                "named",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<String> expectedOrgs = asList(
                "сицилийской мафии",
                "The Telegraph",
                "Cosa Nostra",
                "издание",
                "полиции",
                "органов правопорядка",
                "мафия",
                "Преступная организация",
                "она"
        );
        List<String> expectedOrgCanonicals = asList(
                "сицилийская мафия",
                null,
                null,
                null,
                null,
                "органы правопорядка",
                null,
                null,
                null
        );
        List<String> expectedGPEs = asList(
                "Палермо",
                "Сицилии"
        );
        List<String> expectedTimes = asList(
                "в начале XIX века",
                "к началу XX века"
        );
        Type perType = cas.getTypeSystem().getType(Person.class.getName());
        Type orgType = cas.getTypeSystem().getType(Organization.class.getName());
        assertEquals(expectedPersons,
                transform(newArrayList(select(jCas, Person.class)), coveredTextFunction()));
        assertEquals(expectedPerMentionTypes,
                transform(
                        newArrayList(select(jCas, Person.class)),
                        FSUtils.stringFeatureFunction(perType, "mentionType")));
        assertEquals(
                expectedOrgs,
                transform(newArrayList(select(jCas, Organization.class)), coveredTextFunction()));
        assertEquals(
                expectedOrgCanonicals,
                transform(
                        newArrayList(select(jCas, Organization.class)),
                        FSUtils.stringFeatureFunction(orgType, "canonical")));
        assertEquals(expectedGPEs,
                transform(newArrayList(select(jCas, GPE.class)), coveredTextFunction()));
        assertEquals(expectedTimes,
                transform(newArrayList(select(jCas, Time.class)), coveredTextFunction()));
        // test FS-valued features
        assertEquals(findByCoveredText(jCas, PersonName.class, "Доменико Палацотто"),
                findByCoveredText(jCas, Person.class, "мафиози из Палермо Доменико Палацотто").getName());
        assertEquals(findByCoveredText(jCas, PersonName.class, "Палацотто"),
                findByCoveredText(jCas, Person.class, "Палацотто").getName());
        // test FS-array-valued features
        assertEquals(
                // expected
                Lists.newArrayList(
                        findByCoveredText(jCas, Word.class, "молодые"),
                        findByCoveredText(jCas, Word.class, "мафиози")
                ),
                // actual
                FSCollectionFactory.create(
                        findByCoveredText(jCas, Person.class, "молодые мафиози").getWords(), Word.class));
    }

}
