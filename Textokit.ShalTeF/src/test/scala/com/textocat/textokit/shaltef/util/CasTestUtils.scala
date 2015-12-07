package com.textocat.textokit.shaltef.util

import org.apache.uima.cas.TypeSystem
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory._
import org.apache.uima.jcas.JCas
import org.apache.uima.util.CasCreationUtils

/**
 * @author Rinat Gareev
 */
trait CasTestUtils {

  protected def loadTypeSystem(names: String*): TypeSystem = {
    val tsDesc = createTypeSystemDescription(names: _*)
    val dumbCas = CasCreationUtils.createCas(tsDesc, null, null)
    dumbCas.getTypeSystem
  }

  protected def createJCas(ts: TypeSystem): JCas =
    CasCreationUtils.createCas(ts, null, null, null).getJCas
}