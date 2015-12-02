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

package com.textocat.textokit.eval.cas;

import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * A {@link CasDirectory} implementation that read only files from the specified
 * 'dir' that have names from the specified 'listFile'.
 *
 * @author Rinat Gareev
 */
public class FileListCasDirectory extends FSCasDirectory {

    // config fields
    private File listFile;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        listFile = env.getProperty(beanName + ".listFile", File.class);
        if (listFile == null) {
            throw new IllegalStateException("listFile property value is not specified");
        }
        if (!listFile.isFile()) {
            throw new IllegalStateException(String.format(
                    "%s is not an existing file", listFile));
        }
    }

    @Override
    protected IOFileFilter getSourceFileFilter() {
        final Set<String> included = Sets.newHashSet();
        List<String> includedSrcList;
        try {
            includedSrcList = FileUtils.readLines(listFile, "utf-8");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        for (String p : includedSrcList) {
            if (StringUtils.isBlank(p)) {
                continue;
            }
            File pFile = new File(p);
            if (pFile.isAbsolute()) {
                included.add(pFile.getAbsolutePath());
            } else {
                included.add(new File(dir, p).getAbsolutePath());
            }
        }
        return FileFilterUtils.asFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return included.contains(f.getAbsolutePath());
            }
        });
    }
}
