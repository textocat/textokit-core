/* First created by JCasGen Thu Nov 12 00:10:20 MSK 2015 */
package com.textocat.textokit.tokenizer.fstype;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;

/**
 * Updated by JCasGen Thu Nov 12 00:10:20 MSK 2015
 *
 * @generated
 */
public class WhiteSpace_Type extends TokenBase_Type {
    /**
     * @generated
     */
    @SuppressWarnings("hiding")
    public final static int typeIndexID = WhiteSpace.typeIndexID;
    /**
     * @generated
     * @modifiable
     */
    @SuppressWarnings("hiding")
    public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.textocat.textokit.tokenizer.fstype.WhiteSpace");
    /**
     * @generated
     */
    private final FSGenerator fsGenerator =
            new FSGenerator() {
                public FeatureStructure createFS(int addr, CASImpl cas) {
                    if (WhiteSpace_Type.this.useExistingInstance) {
                        // Return eq fs instance if already created
                        FeatureStructure fs = WhiteSpace_Type.this.jcas.getJfsFromCaddr(addr);
                        if (null == fs) {
                            fs = new WhiteSpace(addr, WhiteSpace_Type.this);
                            WhiteSpace_Type.this.jcas.putJfsFromCaddr(addr, fs);
                            return fs;
                        }
                        return fs;
                    } else return new WhiteSpace(addr, WhiteSpace_Type.this);
                }
            };
    /**
     * initialize variables to correspond with Cas Type and Features
     *
     * @param jcas    JCas
     * @param casType Type
     * @generated
     */
    public WhiteSpace_Type(JCas jcas, Type casType) {
        super(jcas, casType);
        casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl) this.casType, getFSGenerator());

    }

    /**
     * @return the generator for this type
     * @generated
     */
    @Override
    protected FSGenerator getFSGenerator() {
        return fsGenerator;
    }
}



    