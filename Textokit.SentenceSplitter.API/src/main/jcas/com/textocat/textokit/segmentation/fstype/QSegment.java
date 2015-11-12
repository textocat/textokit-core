/* First created by JCasGen Thu Nov 12 17:05:14 MSK 2015 */
package com.textocat.textokit.segmentation.fstype;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/**
 * Updated by JCasGen Thu Nov 12 17:05:14 MSK 2015
 * XML source: src/main/resources/com/textocat/textokit/segmentation/segmentation-TypeSystem.xml
 *
 * @generated
 */
public class QSegment extends Annotation {
    /**
     * @generated
     * @ordered
     */
    @SuppressWarnings("hiding")
    public final static int typeIndexID = JCasRegistry.register(QSegment.class);
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
    protected QSegment() {/* intentionally empty block */}

    /**
     * Internal - constructor used by generator
     *
     * @param addr low level Feature Structure reference
     * @param type the type of this Feature Structure
     * @generated
     */
    public QSegment(int addr, TOP_Type type) {
        super(addr, type);
        readObject();
    }

    /**
     * @param jcas JCas to which this Feature Structure belongs
     * @generated
     */
    public QSegment(JCas jcas) {
        super(jcas);
        readObject();
    }

    /**
     * @param jcas  JCas to which this Feature Structure belongs
     * @param begin offset to the begin spot in the SofA
     * @param end   offset to the end spot in the SofA
     * @generated
     */
    public QSegment(JCas jcas, int begin, int end) {
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
    //* Feature: contentBegin

    /**
     * getter for contentBegin - gets
     *
     * @return value of the feature
     * @generated
     */
    public int getContentBegin() {
        if (QSegment_Type.featOkTst && ((QSegment_Type) jcasType).casFeat_contentBegin == null)
            jcasType.jcas.throwFeatMissing("contentBegin", "com.textocat.textokit.segmentation.fstype.QSegment");
        return jcasType.ll_cas.ll_getIntValue(addr, ((QSegment_Type) jcasType).casFeatCode_contentBegin);
    }

    /**
     * setter for contentBegin - sets
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setContentBegin(int v) {
        if (QSegment_Type.featOkTst && ((QSegment_Type) jcasType).casFeat_contentBegin == null)
            jcasType.jcas.throwFeatMissing("contentBegin", "com.textocat.textokit.segmentation.fstype.QSegment");
        jcasType.ll_cas.ll_setIntValue(addr, ((QSegment_Type) jcasType).casFeatCode_contentBegin, v);
    }


    //*--------------*
    //* Feature: contentEnd

    /**
     * getter for contentEnd - gets
     *
     * @return value of the feature
     * @generated
     */
    public int getContentEnd() {
        if (QSegment_Type.featOkTst && ((QSegment_Type) jcasType).casFeat_contentEnd == null)
            jcasType.jcas.throwFeatMissing("contentEnd", "com.textocat.textokit.segmentation.fstype.QSegment");
        return jcasType.ll_cas.ll_getIntValue(addr, ((QSegment_Type) jcasType).casFeatCode_contentEnd);
    }

    /**
     * setter for contentEnd - sets
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setContentEnd(int v) {
        if (QSegment_Type.featOkTst && ((QSegment_Type) jcasType).casFeat_contentEnd == null)
            jcasType.jcas.throwFeatMissing("contentEnd", "com.textocat.textokit.segmentation.fstype.QSegment");
        jcasType.ll_cas.ll_setIntValue(addr, ((QSegment_Type) jcasType).casFeatCode_contentEnd, v);
    }


    //*--------------*
    //* Feature: parentSegment

    /**
     * getter for parentSegment - gets
     *
     * @return value of the feature
     * @generated
     */
    public Annotation getParentSegment() {
        if (QSegment_Type.featOkTst && ((QSegment_Type) jcasType).casFeat_parentSegment == null)
            jcasType.jcas.throwFeatMissing("parentSegment", "com.textocat.textokit.segmentation.fstype.QSegment");
        return (Annotation) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QSegment_Type) jcasType).casFeatCode_parentSegment)));
    }

    /**
     * setter for parentSegment - sets
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setParentSegment(Annotation v) {
        if (QSegment_Type.featOkTst && ((QSegment_Type) jcasType).casFeat_parentSegment == null)
            jcasType.jcas.throwFeatMissing("parentSegment", "com.textocat.textokit.segmentation.fstype.QSegment");
        jcasType.ll_cas.ll_setRefValue(addr, ((QSegment_Type) jcasType).casFeatCode_parentSegment, jcasType.ll_cas.ll_getFSRef(v));
    }
}

    