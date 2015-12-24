
/* First created by JCasGen Thu Dec 24 18:21:01 MSK 2015 */
package test;

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
 * Updated by JCasGen Thu Dec 24 18:21:01 MSK 2015
 * @generated */
public class Organization_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Organization_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Organization_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Organization(addr, Organization_Type.this);
  			   Organization_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Organization(addr, Organization_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Organization.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("test.Organization");
 
  /** @generated */
  final Feature casFeat_canonical;
  /** @generated */
  final int     casFeatCode_canonical;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCanonical(int addr) {
        if (featOkTst && casFeat_canonical == null)
      jcas.throwFeatMissing("canonical", "test.Organization");
    return ll_cas.ll_getStringValue(addr, casFeatCode_canonical);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCanonical(int addr, String v) {
        if (featOkTst && casFeat_canonical == null)
      jcas.throwFeatMissing("canonical", "test.Organization");
    ll_cas.ll_setStringValue(addr, casFeatCode_canonical, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Organization_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_canonical = jcas.getRequiredFeatureDE(casType, "canonical", "uima.cas.String", featOkTst);
    casFeatCode_canonical  = (null == casFeat_canonical) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_canonical).getCode();

  }
}



    