package com.textocat.textokit.eval.cas;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.TypeSystem;

import java.util.Iterator;

/**
 * @author Rinat Gareev
 */
public interface CasDirectory {
    CAS getCas(String docUri) throws Exception;

    Iterator<CAS> iterator();

    /**
     * @return the count of CAS instances or -1 if it is not available.
     */
    int size();

    void setTypeSystem(TypeSystem ts);

    void init();
}