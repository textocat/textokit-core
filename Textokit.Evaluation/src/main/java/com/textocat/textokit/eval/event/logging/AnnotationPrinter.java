package com.textocat.textokit.eval.event.logging;

import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;

/**
 * @author Rinat Gareev
 */
public interface AnnotationPrinter {

    void init(TypeSystem ts);

    String getString(AnnotationFS anno);

}