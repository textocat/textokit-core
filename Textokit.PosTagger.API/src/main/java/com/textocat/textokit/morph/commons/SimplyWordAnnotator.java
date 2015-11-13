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
