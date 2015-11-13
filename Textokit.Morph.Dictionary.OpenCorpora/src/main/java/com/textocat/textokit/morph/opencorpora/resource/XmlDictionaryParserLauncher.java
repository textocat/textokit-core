/**
 *
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.textocat.textokit.commons.cli.ClassConverter;
import com.textocat.textokit.commons.cli.FileValueValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static java.lang.System.currentTimeMillis;

/**
 * @author Rinat Gareev
 */
public class XmlDictionaryParserLauncher {

    private static final Logger log = LoggerFactory.getLogger(XmlDictionaryParserLauncher.class);

    @Parameter(names = {"-i", "--input-xml"}, required = true, validateValueWith = FileValueValidator.class)
    private File dictXmlFile;
    @Parameter(names = {"--dict-extension-class"}, required = false, converter = ClassConverter.class)
    private Class<? extends DictionaryExtension> dictExtensionClass = DefaultDictionaryExtension.class;
    @Parameter(names = {"-o", "--output-file"}, required = true)
    private File outputFile;

    private XmlDictionaryParserLauncher() {
    }

    public static void main(String[] args) throws Exception {
        XmlDictionaryParserLauncher cfg = new XmlDictionaryParserLauncher();
        new JCommander(cfg, args);

        MorphDictionaryImpl dict = new MorphDictionaryImpl();
        DictionaryExtension ext = cfg.dictExtensionClass.newInstance();
        FileInputStream fis = FileUtils.openInputStream(cfg.dictXmlFile);
        try {
            new XmlDictionaryParser(dict, ext, fis).run();
        } finally {
            IOUtils.closeQuietly(fis);
        }

        log.info("Preparing to serialization...");
        long timeBefore = currentTimeMillis();
        OutputStream fout = new BufferedOutputStream(FileUtils.openOutputStream(cfg.outputFile),
                8192 * 8);
        ObjectOutputStream out = new ObjectOutputStream(fout);
        try {
            out.writeObject(dict.getGramModel());
            out.writeObject(dict);
        } finally {
            out.close();
        }
        log.info("Serialization finished in {} ms.\nOutput size: {} bytes",
                currentTimeMillis() - timeBefore, cfg.outputFile.length());
    }
}
