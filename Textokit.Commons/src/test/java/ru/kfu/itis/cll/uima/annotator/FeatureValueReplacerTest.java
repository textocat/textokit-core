/**
 *
 */
package ru.kfu.itis.cll.uima.annotator;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.kfu.itis.cll.uima.commons.DocumentMetadata;

import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static ru.kfu.itis.cll.uima.annotator.FeatureValueReplacer.*;

/**
 * @author Rinat Gareev
 */
public class FeatureValueReplacerTest {

    private static TypeSystemDescription tsd;

    @BeforeClass
    public static void beforeEveryTest() {
        tsd = createTypeSystemDescription(
                "ru.kfu.itis.cll.uima.commons.Commons-TypeSystem");
    }

    @Test
    public void test() throws UIMAException {
        AnalysisEngine ae = AnalysisEngineFactory.createEngine(FeatureValueReplacer.class, tsd,
                PARAM_ANNO_TYPE, DocumentMetadata.class.getName(),
                PARAM_FEATURE_PATH, "sourceUri",
                PARAM_PATTERN, "file:.+/([^/]+)$",
                PARAM_REPLACE_BY, "$1");
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