package com.textocat.textokit.corpus.statistics.app;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class XmiCorpusUnitsExtractorParams {
    @Parameter(names = "-corpus", description = "Corpus directory.")
    public String corpus;

    @Parameter(names = "-unit", description = "Unit type")
    public List<String> units = new ArrayList<String>();

    @Parameter(names = "-class", description = "Class type")
    public List<String> classes = new ArrayList<String>();

    @Parameter(names = "-output", description = "Output TSV file.")
    public String output;
}
