

/* First created by JCasGen Mon Jun 27 12:19:55 MSK 2016 */
package com.textocat.textokit.corpus.statistics.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Unit for agreement measurement (Token, Sentence, etc.)
 * Updated by JCasGen Mon Jun 27 12:19:55 MSK 2016
 * XML source: src/main/resources/com/textocat/textokit/corpus/statistics/type/Unit.xml
 * @generated */
public class Unit extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Unit.class);
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
  protected Unit() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Unit(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Unit(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Unit(JCas jcas, int begin, int end) {
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
  //* Feature: annotatorClass

  /** getter for annotatorClass - gets Class of unit assigned by annotator
   * @generated
   * @return value of the feature 
   */
  public String getAnnotatorClass() {
    if (Unit_Type.featOkTst && ((Unit_Type)jcasType).casFeat_annotatorClass == null)
      jcasType.jcas.throwFeatMissing("annotatorClass", "com.textocat.textokit.corpus.statistics.type.Unit");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Unit_Type)jcasType).casFeatCode_annotatorClass);}
    
  /** setter for annotatorClass - sets Class of unit assigned by annotator 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnnotatorClass(String v) {
    if (Unit_Type.featOkTst && ((Unit_Type)jcasType).casFeat_annotatorClass == null)
      jcasType.jcas.throwFeatMissing("annotatorClass", "com.textocat.textokit.corpus.statistics.type.Unit");
    jcasType.ll_cas.ll_setStringValue(addr, ((Unit_Type)jcasType).casFeatCode_annotatorClass, v);}    
  }

    