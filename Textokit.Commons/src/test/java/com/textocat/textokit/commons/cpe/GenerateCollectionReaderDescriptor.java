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