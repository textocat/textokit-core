

/* First created by JCasGen Thu Dec 24 18:21:01 MSK 2015 */
package test;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Dec 24 18:21:01 MSK 2015
 * XML source: src/test/resources/test/entities-ts.xml
 * @generated */
public class Person extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Person.class);
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
  protected Person() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Person(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Person(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Person(JCas jcas, int begin, int end) {
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
  //* Feature: mentionType

  /** getter for mentionType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMentionType() {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_mentionType == null)
      jcasType.jcas.throwFeatMissing("mentionType", "test.Person");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Person_Type)jcasType).casFeatCode_mentionType);}
    
  /** setter for mentionType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentionType(String v) {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_mentionType == null)
      jcasType.jcas.throwFeatMissing("mentionType", "test.Person");
    jcasType.ll_cas.ll_setStringValue(addr, ((Person_Type)jcasType).casFeatCode_mentionType, v);}    
   
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets 
   * @generated
   * @return value of the feature 
   */
  public PersonName getName() {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "test.Person");
    return (PersonName)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Person_Type)jcasType).casFeatCode_name)));}
    
  /** setter for name - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(PersonName v) {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "test.Person");
    jcasType.ll_cas.ll_setRefValue(addr, ((Person_Type)jcasType).casFeatCode_name, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: words

  /** getter for words - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getWords() {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_words == null)
      jcasType.jcas.throwFeatMissing("words", "test.Person");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Person_Type)jcasType).casFeatCode_words)));}
    
  /** setter for words - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWords(FSArray v) {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_words == null)
      jcasType.jcas.throwFeatMissing("words", "test.Person");
    jcasType.ll_cas.ll_setRefValue(addr, ((Person_Type)jcasType).casFeatCode_words, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for words - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Word getWords(int i) {
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_words == null)
      jcasType.jcas.throwFeatMissing("words", "test.Person");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Person_Type)jcasType).casFeatCode_words), i);
    return (Word)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Person_Type)jcasType).casFeatCode_words), i)));}

  /** indexed setter for words - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setWords(int i, Word v) { 
    if (Person_Type.featOkTst && ((Person_Type)jcasType).casFeat_words == null)
      jcasType.jcas.throwFeatMissing("words", "test.Person");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Person_Type)jcasType).casFeatCode_words), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Person_Type)jcasType).casFeatCode_words), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    