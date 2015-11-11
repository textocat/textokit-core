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


package com.textocat.textokit.commons.annotator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;

/**
 * An annotator that checks whether a document text contains '%ERROR%' substring
 * and if it does then throws {@link AnalysisEngineProcessException}.
 * <p/>
 * To be used in tests.
 *
 * @author Rinat Gareev
 */
public class ErrorProducer extends CasAnnotator_ImplBase {

    public static final String ERROR_SUBSTRING = "%ERROR%";

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        if (cas.getDocumentText().contains(ERROR_SUBSTRING)) {
            throw new AnalysisEngineProcessException(
                    new Exception("ErrorProducer is on duty!"));
        }
    }
}
