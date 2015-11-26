
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

package com.textocat.textokit.io.brat;

import java.io.File;

/**
 * @author Rinat Gareev
 */
public class BratDocument {

    public final static String TXT_FILE_SUFFIX = ".txt";
    public final static String ANN_FILE_SUFFIX = ".ann";

    private String documentName;
    private File txtFile;
    private File annFile;

    public BratDocument(File dir, String baseName) {
        this.documentName = baseName;
        this.txtFile = new File(dir, baseName + TXT_FILE_SUFFIX);
        this.annFile = new File(dir, baseName + ANN_FILE_SUFFIX);
    }

    public boolean exists() {
        return txtFile.isFile() && annFile.isFile();
    }

    public String getDocumentName() {
        return documentName;
    }

    public File getTxtFile() {
        return txtFile;
    }

    public File getAnnFile() {
        return annFile;
    }
}