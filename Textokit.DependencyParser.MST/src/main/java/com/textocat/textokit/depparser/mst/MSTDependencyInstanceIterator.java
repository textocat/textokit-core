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

package com.textocat.textokit.depparser.mst;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import com.textocat.textokit.commons.io.IoUtils;
import mstparser.DependencyInstance;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Rinat Gareev
 */
public class MSTDependencyInstanceIterator extends AbstractIterator<DependencyInstance>
        implements Closeable {
    // config fields
    private File inputFile;
    private final Logger log = LoggerFactory.getLogger(getClass());
    // state fields
    private BufferedReader reader;
    int currentLine;

    public MSTDependencyInstanceIterator(File inputFile) throws IOException {
        super();
        this.inputFile = inputFile;
        reader = IoUtils.openReader(inputFile);
    }

    @Override
    protected DependencyInstance computeNext() {
        // surface forms line
        String line = readNextLine();
        if (line == null) {
            return endOfData();
        }
        List<String> forms = newArrayList(mstTokenSplitter.split(line));
        // pos-tags line
        line = readNextLine();
        if (line == null) {
            log.warn("Unexpected end of file at line {}", currentLine);
            return endOfData();
        }
        List<String> tags = newArrayList(mstTokenSplitter.split(line));
        // heads line
        line = readNextLine();
        if (line == null) {
            log.warn("Unexpected end of file at line {}", currentLine);
            return endOfData();
        }
        List<Integer> heads;
        {
            List<String> headStrings = newArrayList(mstTokenSplitter.split(line));
            heads = Lists.transform(headStrings, new Function<String, Integer>() {
                @Override
                public Integer apply(String arg) {
                    return Integer.valueOf(arg);
                }
            });
        }
        //
        if (forms.size() != tags.size() || forms.size() != heads.size()) {
            throw new IllegalStateException(String.format(
                    "Different size of the sequences at lines %s-%s",
                    currentLine - 2, currentLine));
        }
        // read delimiter line
        readNextLine();
        return new DependencyInstance(
                forms.toArray(new String[forms.size()]),
                tags.toArray(new String[tags.size()]),
                null,
                ArrayUtils.toPrimitive(heads.toArray(new Integer[heads.size()])));
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(reader);
    }

    private String readNextLine() {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        if (line != null) {
            currentLine++;
        }
        return line;
    }

    private static final Splitter mstTokenSplitter = Splitter.on('\t');
}
