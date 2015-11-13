package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.model.Wordform;

import java.util.List;

public interface WordformPredictor {
    List<Wordform> predict(String str, WordformTSTSearchResult result);
}
