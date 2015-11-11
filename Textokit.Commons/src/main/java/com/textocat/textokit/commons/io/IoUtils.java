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


package com.textocat.textokit.commons.io;

import com.google.common.base.Preconditions;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.io.FileUtils.openOutputStream;
import static org.apache.commons.io.IOUtils.closeQuietly;

/**
 * Some more utils. Lower case 'o' is to avoid name conflicts with other
 * IOUtils. Default encoding for overloaded methods is always UTF-8.
 *
 * @author Rinat Gareev
 */
public class IoUtils {

    private IoUtils() {
    }

    public static PrintWriter openPrintWriter(File file) throws IOException {
        return openPrintWriter(file, "utf-8", false);
    }

    public static PrintWriter openPrintWriter(File file, boolean append) throws IOException {
        return openPrintWriter(file, "utf-8", append);
    }

    public static PrintWriter openPrintWriter(File file, String encoding, boolean append)
            throws IOException {
        return new PrintWriter(openBufferedWriter(file, encoding, append));
    }

    public static BufferedWriter openBufferedWriter(File file) throws IOException {
        return openBufferedWriter(file, "utf-8", false);
    }

    public static BufferedWriter openBufferedWriter(File file, String encoding, boolean append)
            throws IOException {
        FileOutputStream fos = openOutputStream(file, append);
        OutputStreamWriter osr;
        try {
            osr = new OutputStreamWriter(fos, encoding);
        } catch (UnsupportedEncodingException e) {
            closeQuietly(fos);
            throw e;
        }
        return new BufferedWriter(osr);
    }

    public static BufferedReader openReader(File file) throws IOException {
        return openReader(file, "utf-8");
    }

    public static BufferedReader openReader(File file, String encoding) throws IOException {
        FileInputStream fis = openInputStream(file);
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(fis, encoding);
        } catch (UnsupportedEncodingException e) {
            closeQuietly(fis);
            throw e;
        }
        return new BufferedReader(isr);
    }

    public static void write(Properties props, File outFile) throws IOException {
        BufferedWriter bw = openBufferedWriter(outFile);
        try {
            props.store(bw, null);
        } finally {
            closeQuietly(bw);
        }
    }

    public static Properties readProperties(File inFile) throws IOException {
        return readProperties(inFile, "utf-8");
    }

    public static Properties readProperties(File inFile, String encoding) throws IOException {
        Properties props = new Properties();
        BufferedReader r = openReader(inFile, encoding);
        try {
            props.load(r);
        } finally {
            closeQuietly(r);
        }
        return props;
    }

    public static void writeProperties(Properties props, File outFile) throws IOException {
        BufferedWriter ow = openBufferedWriter(outFile);
        try {
            props.store(ow, null);
        } finally {
            closeQuietly(ow);
        }
    }

    public static Path addExtension(Path srcPath, String newExt) {
        Preconditions.checkArgument(newExt != null, "extension is null");
        if (srcPath.getNameCount() == 0) {
            return srcPath;
        }
        String filename = srcPath.getFileName().toString();
        return srcPath.resolveSibling(filename + "." + newExt);
    }
}
