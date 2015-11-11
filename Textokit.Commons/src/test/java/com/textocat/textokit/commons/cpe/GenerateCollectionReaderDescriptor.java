/**
 *
 */
package com.textocat.textokit.commons.cpe;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;

/**
 * @author Rinat Gareev
 */
public class GenerateCollectionReaderDescriptor {

    public static void main(String[] args) throws UIMAException, IOException, SAXException {
        String outputPath = "src/main/resources/com/textocat/textokit/commons/cpe/FileDirectoryCollectionReader.xml";
        TypeSystemDescription tsd = createTypeSystemDescription(
                "com.textocat.textokit.commons.Commons-TypeSystem");
        CollectionReaderDescription crDesc = createReaderDescription(
                FileDirectoryCollectionReader.class,
                tsd);

        FileOutputStream out = new FileOutputStream(outputPath);
        try {
            crDesc.toXML(out);
        } finally {
            out.close();
        }
    }
}