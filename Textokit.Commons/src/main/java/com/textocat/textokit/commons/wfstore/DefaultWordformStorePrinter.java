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


package com.textocat.textokit.commons.wfstore;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.*;
import java.util.Map;

import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.io.FileUtils.openOutputStream;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.toBufferedInputStream;
import static org.apache.commons.lang3.SerializationUtils.deserialize;

/**
 * @author Rinat Gareev
 */
@Parameters(separators = " =")
public class DefaultWordformStorePrinter {

    @Parameter(names = "-f", required = true)
    private File serFile;
    @Parameter(names = "-t")
    private File outFile;

    public static void main(String[] args) throws Exception {
        DefaultWordformStorePrinter printer = new DefaultWordformStorePrinter();
        new JCommander(printer).parse(args);
        printer.run();
    }

    private static final String escapeTabs(String src) {
        return src.replaceAll("\t", "\\t");
    }

    private void run() throws Exception {
        // deserialize
        DefaultWordformStore<?> ws = (DefaultWordformStore<?>) deserialize(toBufferedInputStream(
                openInputStream(serFile)));
        // print
        PrintWriter out;
        boolean closeOut;
        if (outFile == null) {
            out = new PrintWriter(System.out, true);
            closeOut = false;
        } else {
            OutputStream os = openOutputStream(outFile);
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(os, "utf-8")), true);
            closeOut = true;
        }
        try {
            for (Map.Entry<String, ?> e : ws.strKeyMap.entrySet()) {
                out.print(escapeTabs(e.getKey()));
                out.print('\t');
                out.print(e.getValue());
                out.println();
            }
        } finally {
            if (closeOut)
                closeQuietly(out);
        }
    }
}