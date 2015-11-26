
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

package com.textocat.textokit.io.brat;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.nlplab.brat.ann.BratNoteAnnotation;

/**
 * @author Rinat Gareev
 */
public interface BratNoteMapper {
    /**
     * This is invoked during annotator (or collection reader) initialization.
     *
     * @param ts
     */
    void typeSystemInit(TypeSystem ts) throws AnalysisEngineProcessException;

    /**
     * This is invoked during UIMA-to-Brat mapping.
     *
     * @param uAnno
     * @return note content to save as {@link BratNoteAnnotation} for brat
     * annotation derived from given uAnno. Can return null - in this
     * case note annotation will not be created.
     */
    String makeNote(AnnotationFS uAnno);

    /**
     * This is invoked during Brat-to-UIMA mapping. Implementors should parse
     * given noteContent to fill given uAnno features.
     *
     * @param uAnno
     * @param noteContent
     */
    void parseNote(AnnotationFS uAnno, String noteContent);
}