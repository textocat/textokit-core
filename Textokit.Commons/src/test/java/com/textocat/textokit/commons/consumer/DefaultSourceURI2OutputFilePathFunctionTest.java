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

package com.textocat.textokit.commons.consumer;

import com.google.common.base.Function;
import com.textocat.textokit.commons.util.DocumentUtils;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Before;
import org.junit.Test;
import com.textocat.textokit.commons.DocumentMetadata;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class DefaultSourceURI2OutputFilePathFunctionTest {
    private Function<DocumentMetadata, Path> func = new DefaultSourceURI2OutputFilePathFunction();

    private DocumentMetadata meta;

    @Before
    public void init() throws ResourceInitializationException, CASException {
        TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription(
                DocumentUtils.TYPESYSTEM_COMMONS);
        CAS cas = CasCreationUtils.createCas(tsd, null, null);
        cas.setDocumentText("Some text");
        JCas jCas = cas.getJCas();
        meta = new DocumentMetadata(jCas, 0, 0);
        meta.addToIndexes();
    }

    @Test
    public void testWithScheme() {
        meta.setSourceUri("file:/some/folder/file.txt");
        Path result = func.apply(meta);
        assertEquals(Paths.get("/some/folder/file.txt"), result);
    }

    @Test
    public void testRelative() {
        meta.setSourceUri("someFolder/someFile");
        Path result = func.apply(meta);
        assertEquals(Paths.get("someFolder/someFile"), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpaqueURIs() {
        meta.setSourceUri("file:someFolder/someFile");
        func.apply(meta);
    }
}
