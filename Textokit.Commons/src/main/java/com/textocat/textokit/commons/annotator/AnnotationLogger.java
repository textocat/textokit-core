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
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.internal.ExtendedLogger;

/**
 * This annotator will print to UIMA logger CAS text and each annotations.
 * Mainly it is useful for tests development.
 *
 * @author Rinat Gareev
 */
public class AnnotationLogger extends CasAnnotator_ImplBase {

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        ExtendedLogger log = getLogger();
        log.info("CAS text:");
        log.info(cas.getDocumentText());
        for (AnnotationFS anno : cas.getAnnotationIndex()) {
            log.info(anno);
        }
        log.info("Logging for particular CAS is finished");
    }

}