

/* First created by JCasGen Mon Jun 27 12:19:54 MSK 2016 */
package com.textocat.textokit.corpus.statistics.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Stores detailed information about the original source
                document from which the current CAS was initialized.
 * Updated by JCasGen Mon Jun 27 12:19:54 MSK 2016
 * XML source: src/main/resources/com/textocat/textokit/corpus/statistics/type/SourceDocumentInformation.xml
 * @generated */
public class SourceDocumentInformation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SourceDocumentInformation.class);
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
  protected SourceDocumentInformation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SourceDocumentInformation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SourceDocumentInformation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SourceDocumentInformation(JCas jcas, int begin, int end) {
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
  //* Feature: uri

  /** getter for uri - gets URI of document.
   * @generated
   * @return value of the feature 
   */
  public String getUri() {
    if (SourceDocumentInformation_Type.featOkTst && ((SourceDocumentInformation_Type)jcasType).casFeat_uri == null)
      jcasType.jcas.throwFeatMissing("uri", "com.textocat.textokit.corpus.statistics.type.SourceDocumentInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceDocumentInformation_Type)jcasType).casFeatCode_uri);}
    
  /** setter for uri - sets URI of document. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setUri(String v) {
    if (SourceDocumentInformation_Type.featOkTst && ((SourceDocumentInformation_Type)jcasType).casFeat_uri == null)
      jcasType.jcas.throwFeatMissing("uri", "com.textocat.textokit.corpus.statistics.type.SourceDocumentInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceDocumentInformation_Type)jcasType).casFeatCode_uri, v);}    
   
    
  //*--------------*
  //* Feature: annotatorId

  /** getter for annotatorId - gets Annotator who annotated this document
   * @generated
   * @return value of the feature 
   */
  public String getAnnotatorId() {
    if (SourceDocumentInformation_Type.featOkTst && ((SourceDocumentInformation_Type)jcasType).casFeat_annotatorId == null)
      jcasType.jcas.throwFeatMissing("annotatorId", "com.textocat.textokit.corpus.statistics.type.SourceDocumentInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceDocumentInformation_Type)jcasType).casFeatCode_annotatorId);}
    
  /** setter for annotatorId - sets Annotator who annotated this document 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnnotatorId(String v) {
    if (SourceDocumentInformation_Type.featOkTst && ((SourceDocumentInformation_Type)jcasType).casFeat_annotatorId == null)
      jcasType.jcas.throwFeatMissing("annotatorId", "com.textocat.textokit.corpus.statistics.type.SourceDocumentInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceDocumentInformation_Type)jcasType).casFeatCode_annotatorId, v);}    
  }

    