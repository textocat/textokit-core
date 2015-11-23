

/* First created by JCasGen Tue Nov 17 01:38:30 MSK 2015 */
package com.textocat.textokit.phrrecog.cas;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import com.textocat.textokit.morph.fs.Wordform;


/** Represents Noun Phrase
 * Updated by JCasGen Tue Nov 17 01:38:30 MSK 2015
 * XML source: src/main/resources/com/textocat/textokit/phrrecog/ts-phrase-recognizer.xml
 * @generated */
public class NounPhrase extends Phrase {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NounPhrase.class);
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
  protected NounPhrase() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NounPhrase(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NounPhrase(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public NounPhrase(JCas jcas, int begin, int end) {
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
  //* Feature: preposition

  /** getter for preposition - gets 
   * @generated
   * @return value of the feature 
   */
  public Wordform getPreposition() {
    if (NounPhrase_Type.featOkTst && ((NounPhrase_Type)jcasType).casFeat_preposition == null)
      jcasType.jcas.throwFeatMissing("preposition", "com.textocat.textokit.phrrecog.cas.NounPhrase");
    return (Wordform)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NounPhrase_Type)jcasType).casFeatCode_preposition)));}
    
  /** setter for preposition - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPreposition(Wordform v) {
    if (NounPhrase_Type.featOkTst && ((NounPhrase_Type)jcasType).casFeat_preposition == null)
      jcasType.jcas.throwFeatMissing("preposition", "com.textocat.textokit.phrrecog.cas.NounPhrase");
    jcasType.ll_cas.ll_setRefValue(addr, ((NounPhrase_Type)jcasType).casFeatCode_preposition, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: particle

  /** getter for particle - gets 
   * @generated
   * @return value of the feature 
   */
  public Wordform getParticle() {
    if (NounPhrase_Type.featOkTst && ((NounPhrase_Type)jcasType).casFeat_particle == null)
      jcasType.jcas.throwFeatMissing("particle", "com.textocat.textokit.phrrecog.cas.NounPhrase");
    return (Wordform)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NounPhrase_Type)jcasType).casFeatCode_particle)));}
    
  /** setter for particle - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setParticle(Wordform v) {
    if (NounPhrase_Type.featOkTst && ((NounPhrase_Type)jcasType).casFeat_particle == null)
      jcasType.jcas.throwFeatMissing("particle", "com.textocat.textokit.phrrecog.cas.NounPhrase");
    jcasType.ll_cas.ll_setRefValue(addr, ((NounPhrase_Type)jcasType).casFeatCode_particle, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    