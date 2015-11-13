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

package com.textocat.textokit.morph.ruscorpora;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author Rinat Gareev
 */
class RusCorporaWordform extends RusCorporaAnnotation {

    private String lex;
    private String pos;
    private Set<String> lexGrammems;
    private Set<String> wordformGrammems;

    public RusCorporaWordform(int begin) {
        super(begin);
    }

    public RusCorporaWordform(int begin, int end) {
        super(begin, end);
    }

    public String getLex() {
        return lex;
    }

    public void setLex(String lex) {
        this.lex = lex;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Set<String> getLexGrammems() {
        return lexGrammems;
    }

    public void setLexGrammems(Set<String> lexGrammems) {
        this.lexGrammems = lexGrammems;
    }

    public Set<String> getWordformGrammems() {
        return wordformGrammems;
    }

    public void setWordformGrammems(Set<String> wordformGrammems) {
        this.wordformGrammems = wordformGrammems;
    }

    public Set<String> getAllGrammems() {
        return Sets.union(lexGrammems, wordformGrammems);
    }
}