

/* First created by JCasGen Mon Feb 15 19:26:22 MSK 2016 */
package com.textocat.textokit.dictmatcher.fs;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Feb 15 19:26:22 MSK 2016
 * XML source: src/main/resources/com/textocat/textokit/dictmatcher/ts-dictmatcher.xml
 * @generated */
public class DictionaryMatch extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DictionaryMatch.class);
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
  protected DictionaryMatch() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DictionaryMatch(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DictionaryMatch(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DictionaryMatch(JCas jcas, int begin, int end) {
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

  /** getter for firstToken - gets 
   * @generated
   * @return value of the feature 
   */
  public Annotation getFirstToken() {
    if (DictionaryMatch_Type.featOkTst && ((DictionaryMatch_Type)jcasType).casFeat_firstToken == null)
      jcasType.jcas.throwFeatMissing("firstToken", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DictionaryMatch_Type)jcasType).casFeatCode_firstToken)));}
    
  /** setter for firstToken - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFirstToken(Annotation v) {
    if (DictionaryMatch_Type.featOkTst && ((DictionaryMatch_Type)jcasType).casFeat_firstToken == null)
      jcasType.jcas.throwFeatMissing("firstToken", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    jcasType.ll_cas.ll_setRefValue(addr, ((DictionaryMatch_Type)jcasType).casFeatCode_firstToken, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tag

  /** getter for tag - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTag() {
    if (DictionaryMatch_Type.featOkTst && ((DictionaryMatch_Type)jcasType).casFeat_tag == null)
      jcasType.jcas.throwFeatMissing("tag", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DictionaryMatch_Type)jcasType).casFeatCode_tag);}
    
  /** setter for tag - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTag(String v) {
    if (DictionaryMatch_Type.featOkTst && ((DictionaryMatch_Type)jcasType).casFeat_tag == null)
      jcasType.jcas.throwFeatMissing("tag", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    jcasType.ll_cas.ll_setStringValue(addr, ((DictionaryMatch_Type)jcasType).casFeatCode_tag, v);}    
   
    
  //*--------------*
  //* Feature: dictionaryRecordId

  /** getter for dictionaryRecordId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDictionaryRecordId() {
    if (DictionaryMatch_Type.featOkTst && ((DictionaryMatch_Type)jcasType).casFeat_dictionaryRecordId == null)
      jcasType.jcas.throwFeatMissing("dictionaryRecordId", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DictionaryMatch_Type)jcasType).casFeatCode_dictionaryRecordId);}
    
  /** setter for dictionaryRecordId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDictionaryRecordId(String v) {
    if (DictionaryMatch_Type.featOkTst && ((DictionaryMatch_Type)jcasType).casFeat_dictionaryRecordId == null)
      jcasType.jcas.throwFeatMissing("dictionaryRecordId", "com.textocat.textokit.dictmatcher.fs.DictionaryMatch");
    jcasType.ll_cas.ll_setStringValue(addr, ((DictionaryMatch_Type)jcasType).casFeatCode_dictionaryRecordId, v);}    
  }

    