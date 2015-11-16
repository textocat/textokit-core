package com.textocat.textokit.phrrecog.util

import org.apache.uima.cas.text.AnnotationFS
import org.apache.uima.jcas.JCas

trait PhraseStringParsersFactory {

  def createParser(jCas: JCas, tokens: Array[AnnotationFS]): PhraseStringParsers

}