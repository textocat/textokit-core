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

package com.textocat.textokit.morph.model;

import java.io.Serializable;

/**
 * @author Rinat Gareev
 */
public class LemmaLink implements Serializable {

    private static final long serialVersionUID = -4181644229398395488L;

    private int fromLemmaId;
    private int toLemmaId;
    private short linkTypeId;

    public LemmaLink(int fromLemmaId, int toLemmaId, short linkTypeId) {
        this.fromLemmaId = fromLemmaId;
        this.toLemmaId = toLemmaId;
        this.linkTypeId = linkTypeId;
    }

    public int getFromLemmaId() {
        return fromLemmaId;
    }

    public int getToLemmaId() {
        return toLemmaId;
    }

    public short getLinkTypeId() {
        return linkTypeId;
    }
}