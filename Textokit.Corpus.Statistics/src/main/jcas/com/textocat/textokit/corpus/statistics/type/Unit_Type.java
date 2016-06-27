
/* First created by JCasGen Mon Jun 27 12:19:55 MSK 2016 */
package com.textocat.textokit.corpus.statistics.type;

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

/** Unit for agreement measurement (Token, Sentence, etc.)
 * Updated by JCasGen Mon Jun 27 12:19:55 MSK 2016
 * @generated */
public class Unit_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Unit_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Unit_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Unit(addr, Unit_Type.this);
  			   Unit_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Unit(addr, Unit_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Unit.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.textocat.textokit.corpus.statistics.type.Unit");
 
  /** @generated */
  final Feature casFeat_annotatorClass;
  /** @generated */
  final int     casFeatCode_annotatorClass;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnnotatorClass(int addr) {
        if (featOkTst && casFeat_annotatorClass == null)
      jcas.throwFeatMissing("annotatorClass", "com.textocat.textokit.corpus.statistics.type.Unit");
    return ll_cas.ll_getStringValue(addr, casFeatCode_annotatorClass);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnnotatorClass(int addr, String v) {
        if (featOkTst && casFeat_annotatorClass == null)
      jcas.throwFeatMissing("annotatorClass", "com.textocat.textokit.corpus.statistics.type.Unit");
    ll_cas.ll_setStringValue(addr, casFeatCode_annotatorClass, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Unit_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_annotatorClass = jcas.getRequiredFeatureDE(casType, "annotatorClass", "uima.cas.String", featOkTst);
    casFeatCode_annotatorClass  = (null == casFeat_annotatorClass) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotatorClass).getCode();

  }
}



    