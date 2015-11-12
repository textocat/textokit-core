package com.textocat.textokit.segmentation;

import com.textocat.textokit.segmentation.heur.ParagraphSplitter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

/**
 * @author Rinat Gareev
 */
public class GenerateParagraphSplitterDescriptor {

    public static void main(String[] args) throws UIMAException, IOException, SAXException {
        String outputPath = "src/main/resources/" + ParagraphSplitter.class.getName().replace('.', '/') + ".xml";
        TypeSystemDescription tsDesc = SentenceSplitterAPI.getTypeSystemDescription();
        AnalysisEngineDescription desc = createEngineDescription(ParagraphSplitter.class, tsDesc);
        FileOutputStream out = FileUtils.openOutputStream(new File(outputPath));
        try {
            desc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}