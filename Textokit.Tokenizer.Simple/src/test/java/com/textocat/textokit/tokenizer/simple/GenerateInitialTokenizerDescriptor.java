package com.textocat.textokit.tokenizer.simple;

import com.textocat.textokit.tokenizer.TokenizerAPI;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;


/**
 * @author Rinat Gareev
 */
public class GenerateInitialTokenizerDescriptor {

    public static void main(String[] args) throws UIMAException, IOException, SAXException {
        String outputPath = "src/main/resources/com/textocat/textokit/tokenizer/simple/InitialTokenizer.xml";
        TypeSystemDescription tsDesc = TokenizerAPI.getTypeSystemDescription();
        AnalysisEngineDescription desc = createEngineDescription(InitialTokenizer.class, tsDesc);
        FileOutputStream out = new FileOutputStream(outputPath);
        try {
            desc.toXML(out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}