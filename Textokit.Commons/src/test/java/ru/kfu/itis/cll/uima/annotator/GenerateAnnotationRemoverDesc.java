/**
 *
 */
package ru.kfu.itis.cll.uima.annotator;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Rinat Gareev
 */
public class GenerateAnnotationRemoverDesc {

    public static void main(String[] args) throws UIMAException, SAXException, IOException {
        AnalysisEngineDescription desc = AnalysisEngineFactory
                .createEngineDescription(AnnotationRemover.class);
        FileOutputStream out = new FileOutputStream(
                "src/main/resources/ru/kfu/itis/cll/uima/annotator/AnnotationRemover.xml");
        try {
            desc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}