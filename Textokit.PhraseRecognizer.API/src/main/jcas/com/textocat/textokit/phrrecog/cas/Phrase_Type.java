
/* First created by JCasGen Tue Nov 17 01:38:30 MSK 2015 */
package com.textocat.textokit.phrrecog.cas;

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

/** Represents typed 'phrase', i.e. a head word with its dependents.
				Phrase annotation should have the same borders with its head word.
 * Updated by JCasGen Tue Nov 17 01:38:30 MSK 2015
 * @generated */
public class Phrase_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Phrase_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Phrase_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Phrase(addr, Phrase_Type.this);
  			   Phrase_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Phrase(addr, Phrase_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Phrase.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.textocat.textokit.phrrecog.cas.Phrase");
 
  /** @generated */
  final Feature casFeat_head;
  /** @generated */
  final int     casFeatCode_head;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getHead(int addr) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "com.textocat.textokit.phrrecog.cas.Phrase");
    return ll_cas.ll_getRefValue(addr, casFeatCode_head);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHead(int addr, int v) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "com.textocat.textokit.phrrecog.cas.Phrase");
    ll_cas.ll_setRefValue(addr, casFeatCode_head, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dependentWords;
  /** @generated */
  final int     casFeatCode_dependentWords;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDependentWords(int addr) {
        if (featOkTst && casFeat_dependentWords == null)
      jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    return ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDependentWords(int addr, int v) {
        if (featOkTst && casFeat_dependentWords == null)
      jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    ll_cas.ll_setRefValue(addr, casFeatCode_dependentWords, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getDependentWords(int addr, int i) {
        if (featOkTst && casFeat_dependentWords == null)
      jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setDependentWords(int addr, int i, int v) {
        if (featOkTst && casFeat_dependentWords == null)
      jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentWords), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_dependentPhrases;
  /** @generated */
  final int     casFeatCode_dependentPhrases;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDependentPhrases(int addr) {
        if (featOkTst && casFeat_dependentPhrases == null)
      jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    return ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDependentPhrases(int addr, int v) {
        if (featOkTst && casFeat_dependentPhrases == null)
      jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    ll_cas.ll_setRefValue(addr, casFeatCode_dependentPhrases, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getDependentPhrases(int addr, int i) {
        if (featOkTst && casFeat_dependentPhrases == null)
      jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setDependentPhrases(int addr, int i, int v) {
        if (featOkTst && casFeat_dependentPhrases == null)
      jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_dependentPhrases), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Phrase_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_head = jcas.getRequiredFeatureDE(casType, "head", "com.textocat.textokit.morph.fs.Wordform", featOkTst);
    casFeatCode_head  = (null == casFeat_head) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_head).getCode();

 
    casFeat_dependentWords = jcas.getRequiredFeatureDE(casType, "dependentWords", "uima.cas.FSArray", featOkTst);
    casFeatCode_dependentWords  = (null == casFeat_dependentWords) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dependentWords).getCode();

 
    casFeat_dependentPhrases = jcas.getRequiredFeatureDE(casType, "dependentPhrases", "uima.cas.FSArray", featOkTst);
    casFeatCode_dependentPhrases  = (null == casFeat_dependentPhrases) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dependentPhrases).getCode();

  }
}



    