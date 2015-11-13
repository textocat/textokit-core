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
import java.util.Comparator;

/**
 * @author Rinat Gareev
 */
public class Grammeme implements Serializable {

    private static final long serialVersionUID = 4295735884264399518L;
    private static int idCounter = 1;

    private String id;
    private String parentId;
    private String alias;
    private String description;
    private int numId;

    public Grammeme(String id, String parentId, String alias, String description) {
        this.id = id;
        this.parentId = parentId;
        this.numId = idCounter++;

        this.alias = alias;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public int getNumId() {
        return numId;
    }

    public String getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
    }

    private static final Comparator<Grammeme> numIdComparator = new Comparator<Grammeme>() {
        @Override
        public int compare(Grammeme first, Grammeme second) {
            // TODO avoid auto-boxing
            return Integer.valueOf(first.getNumId()).compareTo(second.getNumId());
        }
    };

    public static final Comparator<Grammeme> numIdComparator() {
        return numIdComparator;
    }
}