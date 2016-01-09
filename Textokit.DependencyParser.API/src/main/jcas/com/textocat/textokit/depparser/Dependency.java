/* First created by JCasGen Sun Jan 10 01:40:11 MSK 2016 */
package com.textocat.textokit.depparser;

import com.textocat.textokit.morph.fs.Word;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/**
 * Updated by JCasGen Sun Jan 10 01:40:11 MSK 2016
 * XML source: src/main/resources/com/textocat/textokit/depparser/dependency-ts.xml
 *
 * @generated
 */
public class Dependency extends Annotation {
    /**
     * @generated
     * @ordered
     */
    @SuppressWarnings("hiding")
    public final static int typeIndexID = JCasRegistry.register(Dependency.class);
    /**
     * @generated
     * @ordered
     */
    @SuppressWarnings("hiding")
    public final static int type = typeIndexID;

    /**
     * @return index of the type
     * @generated
     */
    @Override
    public int getTypeIndexID() {
        return typeIndexID;
    }

    /**
     * Never called.  Disable default constructor
     *
     * @generated
     */
    protected Dependency() {/* intentionally empty block */}

    /**
     * Internal - constructor used by generator
     *
     * @param addr low level Feature Structure reference
     * @param type the type of this Feature Structure
     * @generated
     */
    public Dependency(int addr, TOP_Type type) {
        super(addr, type);
        readObject();
    }

    /**
     * @param jcas JCas to which this Feature Structure belongs
     * @generated
     */
    public Dependency(JCas jcas) {
        super(jcas);
        readObject();
    }

    /**
     * @param jcas  JCas to which this Feature Structure belongs
     * @param begin offset to the begin spot in the SofA
     * @param end   offset to the end spot in the SofA
     * @generated
     */
    public Dependency(JCas jcas, int begin, int end) {
        super(jcas);
        setBegin(begin);
        setEnd(end);
        readObject();
    }

    /**
     * <!-- begin-user-doc -->
     * Write your own initialization here
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    private void readObject() {/*default - does nothing empty block */}


    //*--------------*
    //* Feature: dependent

    /**
     * getter for dependent - gets
     *
     * @return value of the feature
     * @generated
     */
    public Word getDependent() {
        if (Dependency_Type.featOkTst && ((Dependency_Type) jcasType).casFeat_dependent == null)
            jcasType.jcas.throwFeatMissing("dependent", "com.textocat.textokit.depparser.Dependency");
        return (Word) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Dependency_Type) jcasType).casFeatCode_dependent)));
    }

    /**
     * setter for dependent - sets
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setDependent(Word v) {
        if (Dependency_Type.featOkTst && ((Dependency_Type) jcasType).casFeat_dependent == null)
            jcasType.jcas.throwFeatMissing("dependent", "com.textocat.textokit.depparser.Dependency");
        jcasType.ll_cas.ll_setRefValue(addr, ((Dependency_Type) jcasType).casFeatCode_dependent, jcasType.ll_cas.ll_getFSRef(v));
    }


    //*--------------*
    //* Feature: head

    /**
     * getter for head - gets
     *
     * @return value of the feature
     * @generated
     */
    public Word getHead() {
        if (Dependency_Type.featOkTst && ((Dependency_Type) jcasType).casFeat_head == null)
            jcasType.jcas.throwFeatMissing("head", "com.textocat.textokit.depparser.Dependency");
        return (Word) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Dependency_Type) jcasType).casFeatCode_head)));
    }

    /**
     * setter for head - sets
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setHead(Word v) {
        if (Dependency_Type.featOkTst && ((Dependency_Type) jcasType).casFeat_head == null)
            jcasType.jcas.throwFeatMissing("head", "com.textocat.textokit.depparser.Dependency");
        jcasType.ll_cas.ll_setRefValue(addr, ((Dependency_Type) jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));
    }


    //*--------------*
    //* Feature: label

    /**
     * getter for label - gets
     *
     * @return value of the feature
     * @generated
     */
    public String getLabel() {
        if (Dependency_Type.featOkTst && ((Dependency_Type) jcasType).casFeat_label == null)
            jcasType.jcas.throwFeatMissing("label", "com.textocat.textokit.depparser.Dependency");
        return jcasType.ll_cas.ll_getStringValue(addr, ((Dependency_Type) jcasType).casFeatCode_label);
    }

    /**
     * setter for label - sets
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setLabel(String v) {
        if (Dependency_Type.featOkTst && ((Dependency_Type) jcasType).casFeat_label == null)
            jcasType.jcas.throwFeatMissing("label", "com.textocat.textokit.depparser.Dependency");
        jcasType.ll_cas.ll_setStringValue(addr, ((Dependency_Type) jcasType).casFeatCode_label, v);
    }
}

    