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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.textocat.textokit.commons.cli.ClassConverter;
import com.textocat.textokit.commons.cli.FileValueValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import static java.lang.System.currentTimeMillis;

/**
 * An app to parse a xml of OpenCorpora dictionary, serialize it and package into jar file with a metadata
 * required by {@link com.textocat.textokit.morph.opencorpora.OpencorporaMorphDictionaryAPI}.
 *
 * @author Rinat Gareev
 */
public class XmlDictionaryPSP {

    @Parameter(names = {"-i", "--input-xml"}, required = true, validateValueWith = FileValueValidator.class)
    private File dictXmlFile;
    @Parameter(names = {"--dict-extension-class"}, required = false, converter = ClassConverter.class)
    private Class<? extends DictionaryExtension> dictExtensionClass = DefaultDictionaryExtension.class;
    @Parameter(names = {"--output-variant"}, required = true)
    private String variant;
    @Parameter(names = {"-o", "--output-jar"}, required = true)
    private File outputJarFile;

    private XmlDictionaryPSP() {
    }

    public static void main(String[] args) throws Exception {
        XmlDictionaryPSP cfg = new XmlDictionaryPSP();
        new JCommander(cfg, args);

        MorphDictionaryImpl dict = new MorphDictionaryImpl();
        DictionaryExtension ext = cfg.dictExtensionClass.newInstance();
        FileInputStream fis = FileUtils.openInputStream(cfg.dictXmlFile);
        try {
            new XmlDictionaryParser(dict, ext, fis).run();
        } finally {
            IOUtils.closeQuietly(fis);
        }

        System.out.println("Preparing to serialization...");
        long timeBefore = currentTimeMillis();
        OutputStream fout = new BufferedOutputStream(FileUtils.openOutputStream(cfg.outputJarFile),
                8192 * 8);
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().putValue(Attributes.Name.MANIFEST_VERSION.toString(), "1.0");
        manifest.getMainAttributes().putValue("Opencorpora-Dictionary-Version", dict.getVersion());
        manifest.getMainAttributes().putValue("Opencorpora-Dictionary-Revision", dict.getRevision());
        manifest.getMainAttributes().putValue("Opencorpora-Dictionary-Variant", cfg.variant);
        String dictEntryName = String.format("opencorpora-%s-%s-%s.dict.ser",
                dict.getVersion(), dict.getRevision(), cfg.variant);
        JarOutputStream jarOut = new JarOutputStream(fout, manifest);
        jarOut.putNextEntry(new ZipEntry(dictEntryName));
        ObjectOutputStream serOut = new ObjectOutputStream(jarOut);
        try {
            serOut.writeObject(dict.getGramModel());
            serOut.writeObject(dict);
        } finally {
            serOut.flush();
            jarOut.closeEntry();
            serOut.close();
        }
        System.out.println(String.format(
                "Serialization finished in %s ms.\nOutput size: %s bytes",
                currentTimeMillis() - timeBefore, cfg.outputJarFile.length()));
    }
}
