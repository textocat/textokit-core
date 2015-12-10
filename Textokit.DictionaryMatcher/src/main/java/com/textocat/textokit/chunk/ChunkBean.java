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

package com.textocat.textokit.chunk;

import java.util.Objects;

/**
 * @author Rinat Gareev
 */
public class ChunkBean<V> implements Chunk<V> {
    private int start;
    private int end;
    private V meta;

    public ChunkBean(int start, int end, V meta) {
        this.start = start;
        this.end = end;
        this.meta = meta;
    }

    @Override
    public int start() {
        return start;
    }

    @Override
    public int end() {
        return end;
    }

    @Override
    public V metadata() {
        return meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkBean<?> chunkBean = (ChunkBean<?>) o;
        return Objects.equals(start, chunkBean.start) &&
                Objects.equals(end, chunkBean.end) &&
                Objects.equals(meta, chunkBean.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
