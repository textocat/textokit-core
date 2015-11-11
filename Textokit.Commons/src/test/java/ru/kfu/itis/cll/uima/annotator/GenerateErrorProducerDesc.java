/**
 *
 */
package ru.kfu.itis.cll.uima.annotator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * @author Rinat Gareev
 */
public class GenerateErrorProducerDesc {

    public static void main(String[] args) throws ResourceInitializationException, SAXException,
            IOException {
        AnalysisEngineDescription desc = createEngineDescription(ErrorProducer.class);
        FileOutputStream out = FileUtils.openOutputStream(new File(
                "src/main/resources/ru/kfu/itis/cll/uima/annotator/ErrorProducer.xml"));
        try {
            desc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
