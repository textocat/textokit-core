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

package com.textocat.textokit.eval.event;

import javax.annotation.PreDestroy;
import java.io.*;

/**
 * @author Rinat Gareev
 */
public abstract class PrintingEvaluationListener extends DocumentUriHoldingEvaluationListener {

    // config
    private File outputFile;
    // derived
    protected PrintWriter printer;

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    protected void init() throws Exception {
        // init printer
        Writer writer;
        if (outputFile != null) {
            File outputDir = outputFile.getParentFile();
            if (outputDir != null) {
                outputDir.mkdirs();
            }
            OutputStream os = new FileOutputStream(outputFile);
            writer = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
        } else {
            writer = new OutputStreamWriter(System.out);
        }
        printer = new PrintWriter(writer, true);
    }

    @PreDestroy
    protected void clean() {
        // do not close stdout!
        if (outputFile != null && printer != null) {
            printer.close();
            printer = null;
        }
    }
}