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

package com.textocat.textokit.dictmatcher.mensa;

import com.dell.mensa.IMatch;
import com.dell.mensa.ITailBuffer;
import com.dell.mensa.impl.generic.AbstractTextSource;
import com.dell.mensa.impl.generic.AhoCorasickMachine;
import com.google.common.collect.Sets;
import com.textocat.textokit.chunk.Chunk;
import com.textocat.textokit.chunk.ChunkBean;
import com.textocat.textokit.chunk.Chunker;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class MensaChunker<V> implements Chunker<V> {

    private final AhoCorasickMachine<String> machine;

    public MensaChunker(AhoCorasickMachine<String> machine) {
        this.machine = machine;
    }

    @Override
    public Set<Chunk<V>> chunks(Iterable<String> tokens) {
        IteratorTextSource textSrc = null;
        try {
            textSrc = new IteratorTextSource(tokens.iterator());
            textSrc.open();
            Iterator<IMatch<String>> matchIter = machine.matchIterator(textSrc);
            Set<Chunk<V>> res = Sets.newHashSet();
            while (matchIter.hasNext()) {
                IMatch<String> m = matchIter.next();
                res.add(new ChunkBean<>((int) m.getStart(), (int) m.getEnd() - 1, (V) m.getKeyword().getUserData()));
            }
            return res;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                if (textSrc != null) {
                    textSrc.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private class IteratorTextSource extends AbstractTextSource<String> {
        private Iterator<String> srcIter;

        public IteratorTextSource(Iterator<String> srcIter) {
            this.srcIter = srcIter;
        }

        @Override
        protected void closeImpl() throws IOException {
        }

        @Override
        protected void openImpl() throws IOException {
        }

        @Override
        protected String readImpl(ITailBuffer<String> buffer_) throws IOException {
            if (srcIter.hasNext()) {
                String nextToken = srcIter.next();
                buffer_.add(nextToken);
                return nextToken;
            } else {
                return null;
            }
        }
    }
}
