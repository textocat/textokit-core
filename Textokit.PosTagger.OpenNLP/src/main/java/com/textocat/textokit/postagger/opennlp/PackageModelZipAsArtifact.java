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

package com.textocat.textokit.postagger.opennlp;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * @author Rinat Gareev
 */
public class PackageModelZipAsArtifact {

    public static void main(String[] args) throws IOException {
        PackageModelZipAsArtifact cli = new PackageModelZipAsArtifact();
        new JCommander(cli, args);
        Path inputZipPath = Paths.get(cli.inputZipPathStr);
        if (!Files.isRegularFile(inputZipPath)) {
            System.err.println(inputZipPath + " is not an existing file.");
            System.exit(1);
        }
        POSModelJarManifestBean manifestBean = new POSModelJarManifestBean(cli.languageCode, cli.modelVariant);
        Path outputJarPath = inputZipPath.resolveSibling(
                FilenameUtils.getBaseName(inputZipPath.getFileName().toString()) + ".jar");
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(outputJarPath))) {
            JarOutputStream jout = new JarOutputStream(out, manifestBean.toManifest());
            jout.putNextEntry(new ZipEntry(
                    ClasspathPOSModelHolder.getClassPath(
                            manifestBean.getLanguageCode(),
                            manifestBean.getModelVariant())));
            FileUtils.copyFile(inputZipPath.toFile(), jout);
            jout.closeEntry();
            jout.close();
        }
    }

    private PackageModelZipAsArtifact() {
    }

    @Parameter(names = "-i", required = true)
    private String inputZipPathStr;
    @Parameter(names = "--model-lang-code", required = true)
    private String languageCode;
    @Parameter(names = "--model-variant", required = true)
    private String modelVariant;
}
