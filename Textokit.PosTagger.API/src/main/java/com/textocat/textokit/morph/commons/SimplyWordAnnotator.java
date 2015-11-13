package com.textocat.textokit.morph.commons;

import com.textocat.textokit.postagger.MorphCasUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * @author Rinat Gareev
 */
public class SimplyWordAnnotator extends JCasAnnotator_ImplBase {

    public static AnalysisEngineDescription createDescription() throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(SimplyWordAnnotator.class);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        MorphCasUtils.makeSimplyWords(jCas);
    }
}
