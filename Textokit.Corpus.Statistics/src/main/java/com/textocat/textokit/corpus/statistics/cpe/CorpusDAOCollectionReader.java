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

package com.textocat.textokit.corpus.statistics.cpe;

import com.textocat.textokit.corpus.statistics.dao.corpus.CorpusDAO;
import com.textocat.textokit.corpus.statistics.dao.corpus.UriAnnotatorPair;
import com.textocat.textokit.corpus.statistics.type.SourceDocumentInformation;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CorpusDAOCollectionReader extends JCasCollectionReader_ImplBase {

    public final static String CORPUS_DAO_KEY = "CorpusDAO";
    @ExternalResource(key = CORPUS_DAO_KEY)
    private CorpusDAO corpusDAO;

    public static final String SOURCE_DOCUMENT_INFORMATION_TYPE_NAME = "com.textocat.textokit.corpus.statistics.type.SourceDocumentInformation";
    public static final String URI_FEAT_NAME = "uri";
    public static final String ANNOTATOR_ID_FEAT_NAME = "annotatorId";

    private Set<UriAnnotatorPair> uriAnnotatorPairs = new HashSet<UriAnnotatorPair>();
    private Iterator<UriAnnotatorPair> uriAnnotatorPairsIterator;
    private int mCurrentIndex;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            for (URI document : corpusDAO.getDocuments()) {
                for (String annotatorId : corpusDAO.getAnnotatorIds(document)) {
                    uriAnnotatorPairs.add(new UriAnnotatorPair(document,
                            annotatorId));
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new ResourceInitializationException();
        }

        uriAnnotatorPairsIterator = uriAnnotatorPairs.iterator();
        mCurrentIndex = 0;
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        UriAnnotatorPair pair = uriAnnotatorPairsIterator.next();
        try {
            corpusDAO.getDocumentCas(pair.getUri(), pair.getAnnotatorId(), jCas.getCas());
        } catch (SAXException e) {
            getLogger().error(
                    String.format("Exception at %s annotated by %s.",
                            pair.getUri(), pair.getAnnotatorId()));
            e.printStackTrace();
            throw new CollectionException();
        }

        addSourceDocumentInformation(jCas, pair);

        mCurrentIndex++;
    }

    private void addSourceDocumentInformation(JCas jCas, UriAnnotatorPair pair) {
        SourceDocumentInformation sourceDocumentInformation = new SourceDocumentInformation(jCas);
        sourceDocumentInformation.setUri(pair.getUri().toString());
        sourceDocumentInformation.setAnnotatorId(pair.getAnnotatorId());
        sourceDocumentInformation.addToIndexes();
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return uriAnnotatorPairsIterator.hasNext();
    }

    @Override
    public Progress[] getProgress() {
        return new Progress[]{new ProgressImpl(mCurrentIndex,
                uriAnnotatorPairs.size(), Progress.ENTITIES)};
    }

}
