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
