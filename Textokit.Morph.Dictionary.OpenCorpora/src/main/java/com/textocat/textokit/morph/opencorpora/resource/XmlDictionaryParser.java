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

package com.textocat.textokit.morph.opencorpora.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * @author Rinat Gareev
 */
public class XmlDictionaryParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private MorphDictionaryImpl dict;
    private DictionaryExtension ext;
    private InputStream in;

    public XmlDictionaryParser(MorphDictionaryImpl dict, DictionaryExtension ext, InputStream in) {
        this.dict = dict;
        this.ext = ext;
        this.in = in;
    }

    public void run() throws SAXException, IOException {
        SAXParser xmlParser;
        try {
            xmlParser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException e) {
            // should never happen
            throw new IllegalStateException(e);
        }
        XMLReader xmlReader = xmlParser.getXMLReader();

        DictionaryXmlHandler dictHandler = new DictionaryXmlHandler(dict);
        if (ext.getLexemePostprocessors() != null) {
            for (LemmaPostProcessor lpp : ext.getLexemePostprocessors()) {
                dictHandler.addLemmaPostProcessor(lpp);
            }
        }
        if (ext.getGramModelPostProcessors() != null) {
            for (GramModelPostProcessor gmpp : ext.getGramModelPostProcessors()) {
                dictHandler.addGramModelPostProcessor(gmpp);
            }
        }

        xmlReader.setContentHandler(dictHandler);
        InputSource xmlSource = new InputSource(in);
        log.info("About to parse xml dictionary file");
        long timeBefore = currentTimeMillis();
        xmlReader.parse(xmlSource);
        log.info("Parsing finished in {} ms", currentTimeMillis() - timeBefore);
    }

    public static MorphDictionaryImpl parse(InputStream in,
                                            final LemmaPostProcessor... lemmaPostProcessors)
            throws IOException, SAXException {
        return parse(in, new DictionaryExtensionBase() {

            @Override
            public List<LemmaPostProcessor> getLexemePostprocessors() {
                return Arrays.asList(lemmaPostProcessors);
            }
        });
    }

    public static MorphDictionaryImpl parse(InputStream in, DictionaryExtension ext)
            throws IOException, SAXException {
        MorphDictionaryImpl dict = new MorphDictionaryImpl();
        parse(dict, in, ext);
        return dict;
    }

    public static void parse(MorphDictionaryImpl dict, InputStream in, DictionaryExtension ext)
            throws IOException, SAXException {
        new XmlDictionaryParser(dict, ext, in).run();
    }
}
