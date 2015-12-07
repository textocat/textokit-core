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