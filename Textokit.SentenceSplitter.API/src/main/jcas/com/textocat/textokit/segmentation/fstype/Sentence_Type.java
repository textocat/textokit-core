
/* First created by JCasGen Thu Nov 12 17:05:14 MSK 2015 */
package com.textocat.textokit.segmentation.fstype;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Nov 12 17:05:14 MSK 2015
 * @generated */
public class Sentence_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Sentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Sentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Sentence(addr, Sentence_Type.this);
  			   Sentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Sentence(addr, Sentence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Sentence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.textocat.textokit.segmentation.fstype.Sentence");
 
  /** @generated */
  final Feature casFeat_firstToken;
  /** @generated */
  final int     casFeatCode_firstToken;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getFirstToken(int addr) {
        if (featOkTst && casFeat_firstToken == null)
      jcas.throwFeatMissing("firstToken", "com.textocat.textokit.segmentation.fstype.Sentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_firstToken);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFirstToken(int addr, int v) {
        if (featOkTst && casFeat_firstToken == null)
      jcas.throwFeatMissing("firstToken", "com.textocat.textokit.segmentation.fstype.Sentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_firstToken, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lastToken;
  /** @generated */
  final int     casFeatCode_lastToken;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getLastToken(int addr) {
        if (featOkTst && casFeat_lastToken == null)
      jcas.throwFeatMissing("lastToken", "com.textocat.textokit.segmentation.fstype.Sentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_lastToken);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLastToken(int addr, int v) {
        if (featOkTst && casFeat_lastToken == null)
      jcas.throwFeatMissing("lastToken", "com.textocat.textokit.segmentation.fstype.Sentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_lastToken, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Sentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_firstToken = jcas.getRequiredFeatureDE(casType, "firstToken", "com.textocat.textokit.tokenizer.fstype.TokenBase", featOkTst);
    casFeatCode_firstToken  = (null == casFeat_firstToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstToken).getCode();

 
    casFeat_lastToken = jcas.getRequiredFeatureDE(casType, "lastToken", "com.textocat.textokit.tokenizer.fstype.TokenBase", featOkTst);
    casFeatCode_lastToken  = (null == casFeat_lastToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lastToken).getCode();

  }
}



    