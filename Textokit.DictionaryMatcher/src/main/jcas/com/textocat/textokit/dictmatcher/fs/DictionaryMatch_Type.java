
/* First created by JCasGen Mon Feb 15 19:26:22 MSK 2016 */
package com.textocat.textokit.dictmatcher.fs;

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
 * Updated by JCasGen Mon Feb 15 19:26:22 MSK 2016
 * @generated */
public class DictionaryMatch_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DictionaryMatch_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DictionaryMatch_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DictionaryMatch(addr, DictionaryMatch_Type.this);
  			   DictionaryMatch_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DictionaryMatch(addr, DictionaryMatch_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DictionaryMatch.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
 
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
      jcas.throwFeatMissing("firstToken", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    return ll_cas.ll_getRefValue(addr, casFeatCode_firstToken);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFirstToken(int addr, int v) {
        if (featOkTst && casFeat_firstToken == null)
      jcas.throwFeatMissing("firstToken", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    ll_cas.ll_setRefValue(addr, casFeatCode_firstToken, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tag;
  /** @generated */
  final int     casFeatCode_tag;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTag(int addr) {
        if (featOkTst && casFeat_tag == null)
      jcas.throwFeatMissing("tag", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tag);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTag(int addr, String v) {
        if (featOkTst && casFeat_tag == null)
      jcas.throwFeatMissing("tag", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    ll_cas.ll_setStringValue(addr, casFeatCode_tag, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dictionaryRecordId;
  /** @generated */
  final int     casFeatCode_dictionaryRecordId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDictionaryRecordId(int addr) {
        if (featOkTst && casFeat_dictionaryRecordId == null)
      jcas.throwFeatMissing("dictionaryRecordId", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    return ll_cas.ll_getStringValue(addr, casFeatCode_dictionaryRecordId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDictionaryRecordId(int addr, String v) {
        if (featOkTst && casFeat_dictionaryRecordId == null)
      jcas.throwFeatMissing("dictionaryRecordId", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    ll_cas.ll_setStringValue(addr, casFeatCode_dictionaryRecordId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DictionaryMatch_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_firstToken = jcas.getRequiredFeatureDE(casType, "firstToken", "uima.tcas.Annotation", featOkTst);
    casFeatCode_firstToken  = (null == casFeat_firstToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstToken).getCode();

 
    casFeat_tag = jcas.getRequiredFeatureDE(casType, "tag", "uima.cas.String", featOkTst);
    casFeatCode_tag  = (null == casFeat_tag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tag).getCode();

 
    casFeat_dictionaryRecordId = jcas.getRequiredFeatureDE(casType, "dictionaryRecordId", "uima.cas.String", featOkTst);
    casFeatCode_dictionaryRecordId  = (null == casFeat_dictionaryRecordId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dictionaryRecordId).getCode();

  }
}



    