/* First created by JCasGen Thu Nov 12 17:05:14 MSK 2015 */
package com.textocat.textokit.segmentation.fstype;

import com.textocat.textokit.tokenizer.fstype.TokenBase;
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
public class Sentence extends Annotation {
    /**
     * @generated
     * @ordered
     */
    @SuppressWarnings("hiding")
    public final static int typeIndexID = JCasRegistry.register(Sentence.class);
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
    protected Sentence() {/* intentionally empty block */}

    /**
     * Internal - constructor used by generator
     *
     * @param addr low level Feature Structure reference
     * @param type the type of this Feature Structure
     * @generated
     */
    public Sentence(int addr, TOP_Type type) {
        super(addr, type);
        readObject();
    }

    /**
     * @param jcas JCas to which this Feature Structure belongs
     * @generated
     */
    public Sentence(JCas jcas) {
        super(jcas);
        readObject();
    }

    /**
     * @param jcas  JCas to which this Feature Structure belongs
     * @param begin offset to the begin spot in the SofA
     * @param end   offset to the end spot in the SofA
     * @generated
     */
    public Sentence(JCas jcas, int begin, int end) {
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
    //* Feature: firstToken

    /**
     * getter for firstToken - gets the first token of a sentence
     *
     * @return value of the feature
     * @generated
     */
    public TokenBase getFirstToken() {
        if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_firstToken == null)
            jcasType.jcas.throwFeatMissing("firstToken", "com.textocat.textokit.segmentation.fstype.Sentence");
        return (TokenBase) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_firstToken)));
    }

    /**
     * setter for firstToken - sets the first token of a sentence
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setFirstToken(TokenBase v) {
        if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_firstToken == null)
            jcasType.jcas.throwFeatMissing("firstToken", "com.textocat.textokit.segmentation.fstype.Sentence");
        jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_firstToken, jcasType.ll_cas.ll_getFSRef(v));
    }


    //*--------------*
    //* Feature: lastToken

    /**
     * getter for lastToken - gets the last token of a sentence
     *
     * @return value of the feature
     * @generated
     */
    public TokenBase getLastToken() {
        if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_lastToken == null)
            jcasType.jcas.throwFeatMissing("lastToken", "com.textocat.textokit.segmentation.fstype.Sentence");
        return (TokenBase) (jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_lastToken)));
    }

    /**
     * setter for lastToken - sets the last token of a sentence
     *
     * @param v value to set into the feature
     * @generated
     */
    public void setLastToken(TokenBase v) {
        if (Sentence_Type.featOkTst && ((Sentence_Type) jcasType).casFeat_lastToken == null)
            jcasType.jcas.throwFeatMissing("lastToken", "com.textocat.textokit.segmentation.fstype.Sentence");
        jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type) jcasType).casFeatCode_lastToken, jcasType.ll_cas.ll_getFSRef(v));
    }
}

    