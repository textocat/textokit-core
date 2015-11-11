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

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.io.IOUtils.closeQuietly;

/**
 * Auxiliary methods to deal with input/output of external {@link Process}es.
 *
 * @author Rinat Gareev
 */
public class ProcessIOUtils {

    private ProcessIOUtils() {
    }

    public static void feedProcessInput(Process proc, File inFile, boolean closeStdIn)
            throws IOException {
        InputStream in = new BufferedInputStream(openInputStream(inFile));
        feedProcessInput(proc, in, closeStdIn);
    }

    /**
     * @param proc process which input stream will receive bytes from the
     *             argument input stream
     * @param in   input stream. Note that it is closed at the end.
     */
    public static void feedProcessInput(
            Process proc, final InputStream in, final boolean closeStdIn)
            throws IOException {
        final OutputStream procStdIn = proc.getOutputStream();
        final List<Exception> exceptions = Lists.newLinkedList();
        Thread writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IOUtils.copy(in, procStdIn);
                    if (closeStdIn) {
                        procStdIn.flush();
                        closeQuietly(procStdIn);
                    }
                } catch (Exception e) {
                    exceptions.add(e);
                } finally {
                    closeQuietly(in);
                }
            }
        });
        writerThread.start();
        try {
            writerThread.join();
        } catch (InterruptedException e) {
            // do nothing, just set flag
            Thread.currentThread().interrupt();
        }
        if (!exceptions.isEmpty()) {
            Exception ex = exceptions.get(0);
            throw ex instanceof IOException ? (IOException) ex
                    : new IOException("Unexpected exception in writing thread", ex);
        }
    }

}
