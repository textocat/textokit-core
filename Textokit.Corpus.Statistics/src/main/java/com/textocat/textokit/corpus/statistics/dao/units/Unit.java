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

package com.textocat.textokit.corpus.statistics.dao.units;

import com.google.common.collect.Maps;

import java.net.URI;
import java.util.SortedMap;

public class Unit {

    private UnitLocation location;
    private SortedMap<String, String> classByAnnotatorId = Maps.newTreeMap();

    public Unit(UnitLocation location) {
        this.location = location;
    }

    public Unit(UnitLocation location, String annotatorId, String annotatorClass) {
        this(location);
        classByAnnotatorId.put(annotatorId, annotatorClass);
    }

    public UnitLocation getLocation() {
        return location;
    }

    public URI getDocumentURI() {
        return location.getDocumentURI();
    }

    public int getBegin() {
        return location.getBegin();
    }

    public int getEnd() {
        return location.getEnd();
    }

    public SortedMap<String, String> getClassesByAnnotatorId() {
        return classByAnnotatorId;
    }

    public String[] getSortedClasses() {
        return classByAnnotatorId.values().toArray(new String[0]);
    }

    public void putClassByAnnotatorId(String annotatorId, String annotatorClass) {
        classByAnnotatorId.put(annotatorId, annotatorClass);
    }

    @Override
    public String toString() {
        return location.toString() + " " + classByAnnotatorId;
    }
}
