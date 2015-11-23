

/* First created by JCasGen Tue Nov 17 01:38:30 MSK 2015 */
package com.textocat.textokit.phrrecog.cas;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import com.textocat.textokit.morph.fs.Wordform;
import org.apache.uima.jcas.tcas.Annotation;


/** Represents typed 'phrase', i.e. a head word with its dependents.
				Phrase annotation should have the same borders with its head word.
 * Updated by JCasGen Tue Nov 17 01:38:30 MSK 2015
 * XML source: src/main/resources/com/textocat/textokit/phrrecog/ts-phrase-recognizer.xml
 * @generated */
public class Phrase extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Phrase.class);
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
  protected Phrase() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Phrase(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Phrase(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Phrase(JCas jcas, int begin, int end) {
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
  //* Feature: head

  /** getter for head - gets 
   * @generated
   * @return value of the feature 
   */
  public Wordform getHead() {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "com.textocat.textokit.phrrecog.cas.Phrase");
    return (Wordform)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHead(Wordform v) {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.ll_cas.ll_setRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: dependentWords

  /** getter for dependentWords - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getDependentWords() {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentWords == null)
      jcasType.jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentWords)));}
    
  /** setter for dependentWords - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDependentWords(FSArray v) {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentWords == null)
      jcasType.jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.ll_cas.ll_setRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentWords, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for dependentWords - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Wordform getDependentWords(int i) {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentWords == null)
      jcasType.jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentWords), i);
    return (Wordform)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentWords), i)));}

  /** indexed setter for dependentWords - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDependentWords(int i, Wordform v) { 
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentWords == null)
      jcasType.jcas.throwFeatMissing("dependentWords", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentWords), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentWords), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: dependentPhrases

  /** getter for dependentPhrases - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getDependentPhrases() {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentPhrases == null)
      jcasType.jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentPhrases)));}
    
  /** setter for dependentPhrases - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDependentPhrases(FSArray v) {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentPhrases == null)
      jcasType.jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.ll_cas.ll_setRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentPhrases, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for dependentPhrases - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Phrase getDependentPhrases(int i) {
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentPhrases == null)
      jcasType.jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentPhrases), i);
    return (Phrase)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentPhrases), i)));}

  /** indexed setter for dependentPhrases - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDependentPhrases(int i, Phrase v) { 
    if (Phrase_Type.featOkTst && ((Phrase_Type)jcasType).casFeat_dependentPhrases == null)
      jcasType.jcas.throwFeatMissing("dependentPhrases", "com.textocat.textokit.phrrecog.cas.Phrase");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentPhrases), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Phrase_Type)jcasType).casFeatCode_dependentPhrases), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    