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

package com.textocat.textokit.eval.event.logging;

import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;

/**
 * @author Rinat Gareev
 */
public class CoveredTextAnnotationPrinter implements AnnotationPrinter {

    private static final CoveredTextAnnotationPrinter INSTANCE = new CoveredTextAnnotationPrinter();

    public static CoveredTextAnnotationPrinter getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(AnnotationFS anno) {
        return anno == null ? null : anno.getCoveredText();
    }

    @Override
    public void init(TypeSystem ts) {
    }
}