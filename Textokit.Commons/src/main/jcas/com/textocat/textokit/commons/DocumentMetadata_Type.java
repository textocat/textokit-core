
/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/* First created by JCasGen Wed Nov 11 19:41:34 MSK 2015 */
package com.textocat.textokit.commons;

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
 * Updated by JCasGen Wed Nov 11 19:41:34 MSK 2015
 * @generated */
public class DocumentMetadata_Type extends Annotation_Type {
  /** @generated
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator =
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentMetadata_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentMetadata_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentMetadata(addr, DocumentMetadata_Type.this);
  			   DocumentMetadata_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentMetadata(addr, DocumentMetadata_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentMetadata.typeIndexID;
  /** @generated
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.textocat.textokit.commons.DocumentMetadata");

  /** @generated */
  final Feature casFeat_sourceUri;
  /** @generated */
  final int     casFeatCode_sourceUri;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value
   */
  public String getSourceUri(int addr) {
        if (featOkTst && casFeat_sourceUri == null)
      jcas.throwFeatMissing("sourceUri", "com.textocat.textokit.commons.DocumentMetadata");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceUri);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set
   */
  public void setSourceUri(int addr, String v) {
        if (featOkTst && casFeat_sourceUri == null)
      jcas.throwFeatMissing("sourceUri", "com.textocat.textokit.commons.DocumentMetadata");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceUri, v);}



  /** @generated */
  final Feature casFeat_offsetInSource;
  /** @generated */
  final int     casFeatCode_offsetInSource;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value
   */
  public long getOffsetInSource(int addr) {
        if (featOkTst && casFeat_offsetInSource == null)
      jcas.throwFeatMissing("offsetInSource", "com.textocat.textokit.commons.DocumentMetadata");
    return ll_cas.ll_getLongValue(addr, casFeatCode_offsetInSource);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set
   */
  public void setOffsetInSource(int addr, long v) {
        if (featOkTst && casFeat_offsetInSource == null)
      jcas.throwFeatMissing("offsetInSource", "com.textocat.textokit.commons.DocumentMetadata");
    ll_cas.ll_setLongValue(addr, casFeatCode_offsetInSource, v);}



  /** @generated */
  final Feature casFeat_documentSize;
  /** @generated */
  final int     casFeatCode_documentSize;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value
   */
  public long getDocumentSize(int addr) {
        if (featOkTst && casFeat_documentSize == null)
      jcas.throwFeatMissing("documentSize", "com.textocat.textokit.commons.DocumentMetadata");
    return ll_cas.ll_getLongValue(addr, casFeatCode_documentSize);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set
   */
  public void setDocumentSize(int addr, long v) {
        if (featOkTst && casFeat_documentSize == null)
      jcas.throwFeatMissing("documentSize", "com.textocat.textokit.commons.DocumentMetadata");
    ll_cas.ll_setLongValue(addr, casFeatCode_documentSize, v);}



  /** @generated */
  final Feature casFeat_startProcessingTime;
  /** @generated */
  final int     casFeatCode_startProcessingTime;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value
   */
  public long getStartProcessingTime(int addr) {
        if (featOkTst && casFeat_startProcessingTime == null)
      jcas.throwFeatMissing("startProcessingTime", "com.textocat.textokit.commons.DocumentMetadata");
    return ll_cas.ll_getLongValue(addr, casFeatCode_startProcessingTime);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set
   */
  public void setStartProcessingTime(int addr, long v) {
        if (featOkTst && casFeat_startProcessingTime == null)
      jcas.throwFeatMissing("startProcessingTime", "com.textocat.textokit.commons.DocumentMetadata");
    ll_cas.ll_setLongValue(addr, casFeatCode_startProcessingTime, v);}





  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type
	 */
  public DocumentMetadata_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());


    casFeat_sourceUri = jcas.getRequiredFeatureDE(casType, "sourceUri", "uima.cas.String", featOkTst);
    casFeatCode_sourceUri  = (null == casFeat_sourceUri) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceUri).getCode();


    casFeat_offsetInSource = jcas.getRequiredFeatureDE(casType, "offsetInSource", "uima.cas.Long", featOkTst);
    casFeatCode_offsetInSource  = (null == casFeat_offsetInSource) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_offsetInSource).getCode();


    casFeat_documentSize = jcas.getRequiredFeatureDE(casType, "documentSize", "uima.cas.Long", featOkTst);
    casFeatCode_documentSize  = (null == casFeat_documentSize) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_documentSize).getCode();


    casFeat_startProcessingTime = jcas.getRequiredFeatureDE(casType, "startProcessingTime", "uima.cas.Long", featOkTst);
    casFeatCode_startProcessingTime  = (null == casFeat_startProcessingTime) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_startProcessingTime).getCode();

  }
}



    