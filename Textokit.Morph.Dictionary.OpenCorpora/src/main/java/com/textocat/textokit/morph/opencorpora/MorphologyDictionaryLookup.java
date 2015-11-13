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

package com.textocat.textokit.morph.opencorpora;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.textocat.textokit.commons.cli.FileValueValidator;
import com.textocat.textokit.commons.io.IoUtils;
import com.textocat.textokit.morph.dictionary.WordUtils;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.model.Wordform;
import com.textocat.textokit.morph.opencorpora.resource.DictionaryDeserializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.List;

import static com.textocat.textokit.morph.model.Wordform.getAllGramBits;

/**
 * @author Rinat Gareev
 */
public class MorphologyDictionaryLookup {

    public static void main(String[] args) throws Exception {
        MorphologyDictionaryLookup launcher = new MorphologyDictionaryLookup();
        new JCommander(launcher, args);
        launcher.run();
    }

    // config fields
    @Parameter(required = true, validateValueWith = FileValueValidator.class)
    private List<File> srcFiles;
    @Parameter(names = {"-d", "--dict-file"}, required = true)
    private File serializedDictFile;
    // state fields
    private PrintWriter out;

    private void run() throws Exception {
        // read dictionary
        MorphDictionary dict = DictionaryDeserializer.from(serializedDictFile);
        for (File srcFile : srcFiles) {
            // read input
            List<String> srcLines = FileUtils.readLines(srcFile, "utf-8");
            // prepare output
            File outFile = getOutFile(srcFile);
            out = IoUtils.openPrintWriter(outFile);

            try {
                for (String s : srcLines) {
                    s = s.trim();
                    if (s.isEmpty()) {
                        continue;
                    }
                    s = WordUtils.normalizeToDictionaryForm(s);
                    List<Wordform> sEntries = dict.getEntries(s);
                    if (sEntries == null || sEntries.isEmpty()) {
                        writeEntry(s, "?UNKNOWN?");
                        continue;
                    }
                    for (Wordform se : sEntries) {
                        BitSet gramBits = getAllGramBits(se, dict);
                        List<String> grams = dict.getGramModel().toGramSet(gramBits);
                        writeEntry(s, grams);
                    }
                }
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }

    private void writeEntry(String src, String gram) {
        writeEntry(src, ImmutableList.of(gram));
    }

    private void writeEntry(String src, Iterable<String> grams) {
        out.println(String.format("%s\t%s",
                src, gramJoiner.join(grams)));
    }

    private static final Joiner gramJoiner = Joiner.on(',');

    private File getOutFile(File srcFile) {
        File dir = srcFile.getParentFile();
        if (dir == null) {
            dir = new File(".");
        }
        String baseName = FilenameUtils.getBaseName(srcFile.getName());
        return new File(dir, baseName + ".out");
    }
}
