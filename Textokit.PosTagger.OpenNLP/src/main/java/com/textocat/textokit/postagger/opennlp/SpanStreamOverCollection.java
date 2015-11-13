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

import opennlp.tools.util.ObjectStream;
import org.apache.uima.jcas.tcas.Annotation;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Rinat Gareev
 */
public class SpanStreamOverCollection<ST extends Annotation> implements ObjectStream<ST> {

    private final Iterator<ST> spanIter;

    public SpanStreamOverCollection(Iterator<ST> spanIter) {
        this.spanIter = spanIter;
    }

    @Override
    public ST read() throws IOException {
        if (spanIter.hasNext()) {
            return spanIter.next();
        }
        return null;
    }

    @Override
    public void reset() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        if (spanIter instanceof Closeable) {
            ((Closeable) spanIter).close();
        }
    }

}
