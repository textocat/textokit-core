

/* First created by JCasGen Wed Nov 11 19:41:34 MSK 2015 */
package com.textocat.textokit.commons;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Nov 11 19:41:34 MSK 2015
 * XML source: src/main/resources/com/textocat/textokit/commons/Commons-TypeSystem.xml
 * @generated */
public class DocumentMetadata extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentMetadata.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentMetadata() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentMetadata(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentMetadata(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentMetadata(JCas jcas, int begin, int end) {
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
  //* Feature: sourceUri

  /** getter for sourceUri - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSourceUri() {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_sourceUri == null)
      jcasType.jcas.throwFeatMissing("sourceUri", "com.textocat.textokit.commons.DocumentMetadata");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_sourceUri);}
    
  /** setter for sourceUri - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSourceUri(String v) {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_sourceUri == null)
      jcasType.jcas.throwFeatMissing("sourceUri", "com.textocat.textokit.commons.DocumentMetadata");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_sourceUri, v);}    
   
    
  //*--------------*
  //* Feature: offsetInSource

  /** getter for offsetInSource - gets 
   * @generated
   * @return value of the feature 
   */
  public long getOffsetInSource() {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_offsetInSource == null)
      jcasType.jcas.throwFeatMissing("offsetInSource", "com.textocat.textokit.commons.DocumentMetadata");
    return jcasType.ll_cas.ll_getLongValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_offsetInSource);}
    
  /** setter for offsetInSource - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setOffsetInSource(long v) {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_offsetInSource == null)
      jcasType.jcas.throwFeatMissing("offsetInSource", "com.textocat.textokit.commons.DocumentMetadata");
    jcasType.ll_cas.ll_setLongValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_offsetInSource, v);}    
   
    
  //*--------------*
  //* Feature: documentSize

  /** getter for documentSize - gets 
   * @generated
   * @return value of the feature 
   */
  public long getDocumentSize() {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_documentSize == null)
      jcasType.jcas.throwFeatMissing("documentSize", "com.textocat.textokit.commons.DocumentMetadata");
    return jcasType.ll_cas.ll_getLongValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_documentSize);}
    
  /** setter for documentSize - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocumentSize(long v) {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_documentSize == null)
      jcasType.jcas.throwFeatMissing("documentSize", "com.textocat.textokit.commons.DocumentMetadata");
    jcasType.ll_cas.ll_setLongValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_documentSize, v);}    
   
    
  //*--------------*
  //* Feature: startProcessingTime

  /** getter for startProcessingTime - gets 
   * @generated
   * @return value of the feature 
   */
  public long getStartProcessingTime() {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_startProcessingTime == null)
      jcasType.jcas.throwFeatMissing("startProcessingTime", "com.textocat.textokit.commons.DocumentMetadata");
    return jcasType.ll_cas.ll_getLongValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_startProcessingTime);}
    
  /** setter for startProcessingTime - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStartProcessingTime(long v) {
    if (DocumentMetadata_Type.featOkTst && ((DocumentMetadata_Type)jcasType).casFeat_startProcessingTime == null)
      jcasType.jcas.throwFeatMissing("startProcessingTime", "com.textocat.textokit.commons.DocumentMetadata");
    jcasType.ll_cas.ll_setLongValue(addr, ((DocumentMetadata_Type)jcasType).casFeatCode_startProcessingTime, v);}    
  }

    