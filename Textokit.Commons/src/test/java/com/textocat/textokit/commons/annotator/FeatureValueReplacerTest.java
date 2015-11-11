/**
 *
 */
package com.textocat.textokit.commons.annotator;

import com.textocat.textokit.commons.DocumentMetadata;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class FeatureValueReplacerTest {

    private static TypeSystemDescription tsd;

    @BeforeClass
    public static void beforeEveryTest() {
        tsd = createTypeSystemDescription(
                "com.textocat.textokit.commons.Commons-TypeSystem");
    }

    @Test
    public void test() throws UIMAException {
        AnalysisEngine ae = AnalysisEngineFactory.createEngine(FeatureValueReplacer.class, tsd,
                FeatureValueReplacer.PARAM_ANNO_TYPE, DocumentMetadata.class.getName(),
                FeatureValueReplacer.PARAM_FEATURE_PATH, "sourceUri",
                FeatureValueReplacer.PARAM_PATTERN, "file:.+/([^/]+)$",
                FeatureValueReplacer.PARAM_REPLACE_BY, "$1");
        JCas cas = ae.newCAS().getJCas();
        cas.setDocumentText("Bla bla");
        DocumentMetadata metaAnno = new DocumentMetadata(cas);
        metaAnno.setBegin(0);
        metaAnno.setEnd(0);
        metaAnno.setSourceUri("file:/d:/somefolder/somemore/foobar.txt");
        metaAnno.addToIndexes();

        ae.process(cas);

        metaAnno = (DocumentMetadata) cas.getAnnotationIndex(DocumentMetadata.type).iterator().next();
        assertEquals("foobar.txt", metaAnno.getSourceUri());

        // next trial
        cas = ae.newCAS().getJCas();
        cas.setDocumentText("Bla bla more");
        metaAnno = new DocumentMetadata(cas);
        metaAnno.setBegin(0);
        metaAnno.setEnd(0);
        metaAnno.setSourceUri("http://example.org/qwerty.txt");
        metaAnno.addToIndexes();

        ae.process(cas);

        metaAnno = (DocumentMetadata) cas.getAnnotationIndex(DocumentMetadata.type).iterator().next();
        assertEquals("http://example.org/qwerty.txt", metaAnno.getSourceUri());
    }

}