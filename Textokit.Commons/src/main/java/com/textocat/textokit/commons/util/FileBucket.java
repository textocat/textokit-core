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


package com.textocat.textokit.commons.util;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.Collections;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
class FileBucket implements Comparable<FileBucket> {
    private Set<File> files = Sets.newLinkedHashSet();
    // state fields
    private Long size = 0L;

    FileBucket() {
    }

    @Override
    public int compareTo(FileBucket arg) {
        return size.compareTo(arg.size);
    }

    public void add(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException(String.format(
                    "%s is not an existing file", file));
        }
        if (files.add(file)) {
            size += file.length();
        }
    }

    public Set<File> getFiles() {
        return Collections.unmodifiableSet(files);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("filesNum", files.size())
                .add("size", size)
                .toString();
    }
}
