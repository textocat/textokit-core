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


package com.textocat.textokit.commons.util;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.TypeSystemDescription_impl;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;
import com.textocat.textokit.commons.DocumentMetadata;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Rinat Gareev
 */
public class DocumentUtilsTest {

    private TypeSystemDescription inputTS = TypeSystemDescriptionFactory
            .createTypeSystemDescription(
                    "com.textocat.textokit.commons.Commons-TypeSystem");

    @Test
    public void setUriShouldFailIfThereIsNoMeta() throws ResourceInitializationException {
        CAS cas = CasCreationUtils.createCas(inputTS, null, null);
        cas.setDocumentText("Some text");
        assertEquals(null, DocumentUtils.getDocumentUri(cas));
        try {
            DocumentUtils.setDocumentUri(cas, "uri1", false);
        } catch (IllegalStateException e) {
            return;
        }
        fail();
    }

    @Test
    public void setUriShouldCreateIfThereIsNoMeta() throws ResourceInitializationException {
        CAS cas = CasCreationUtils.createCas(inputTS, null, null);
        cas.setDocumentText("Some text");
        assertEquals(null, DocumentUtils.getDocumentUri(cas));
        DocumentUtils.setDocumentUri(cas, "uri1", true);
        assertEquals("uri1", DocumentUtils.getDocumentUri(cas));
    }

    @Test
    public void setUriShouldChangeUri() throws ResourceInitializationException, CASException {
        CAS cas = CasCreationUtils.createCas(inputTS, null, null);
        {
            JCas jCas = cas.getJCas();
            DocumentMetadata dm = new DocumentMetadata(jCas, 0, 0);
            dm.setSourceUri("old_uri");
            dm.addToIndexes();
        }
        cas.setDocumentText("Some text");
        assertEquals("old_uri", DocumentUtils.getDocumentUri(cas));
        DocumentUtils.setDocumentUri(cas, "uri1", false);
        assertEquals("uri1", DocumentUtils.getDocumentUri(cas));
    }

    @Test
    public void setUriShouldFailIfNoTypeDefined() throws ResourceInitializationException {
        TypeSystemDescription inputTS = new TypeSystemDescription_impl();
        CAS cas = CasCreationUtils.createCas(inputTS, null, null);
        cas.setDocumentText("Some text");
        assertEquals(null, DocumentUtils.getDocumentUri(cas));
        try {
            DocumentUtils.setDocumentUri(cas, "uri1", true);
        } catch (IllegalStateException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetFilename() throws URISyntaxException {
        assertEquals("1.txt", DocumentUtils.getFilename("file:corpus/1.txt"));
        assertEquals("1.txt", DocumentUtils.getFilename("corpus/1.txt"));
        assertEquals("1.txt", DocumentUtils.getFilename("file:/corpus/1.txt"));
    }
}
