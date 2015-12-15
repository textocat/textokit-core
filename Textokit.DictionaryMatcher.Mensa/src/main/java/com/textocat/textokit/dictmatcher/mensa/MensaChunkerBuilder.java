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

import com.dell.mensa.impl.generic.AhoCorasickMachine;
import com.dell.mensa.impl.generic.Factory;
import com.dell.mensa.impl.generic.Keyword;
import com.dell.mensa.impl.generic.Keywords;
import com.google.common.collect.Iterables;
import com.textocat.textokit.chunk.Chunker;
import com.textocat.textokit.chunk.ChunkerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rinat Gareev
 */
public class MensaChunkerBuilder<V> implements ChunkerBuilder<V> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Keywords<String> dictEntries = new Keywords<>();
    private int entryCounter = 0;
    private final int loggingFrequency;

    public MensaChunkerBuilder(int loggingFrequency) {
        this.loggingFrequency = loggingFrequency;
    }

    public MensaChunkerBuilder() {
        this(10000);
    }

    @Override
    public void addEntry(Iterable<String> aTokens, V metadata) {
        String[] tokens = Iterables.toArray(aTokens, String.class);
        dictEntries.add(new Keyword<>(tokens, metadata));
        entryCounter += 1;
        if (entryCounter % loggingFrequency == 0) {
            log.info("{} entries have been added", entryCounter);
        }
    }

    @Override
    public Chunker<V> build() {
        AhoCorasickMachine<String> machine = new AhoCorasickMachine<>(new Factory<String>());
        log.info("Building AhoCorasickMachine...");
        machine.build(dictEntries);
        log.info("AhoCorasickMachine has been built.");
        return new MensaChunker<>(machine);
    }
}
