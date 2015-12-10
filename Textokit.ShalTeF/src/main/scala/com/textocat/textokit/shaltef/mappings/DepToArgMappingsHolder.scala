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

package com.textocat.textokit.shaltef.mappings

import com.textocat.textokit.morph.fs.Wordform
import com.textocat.textokit.shaltef.mappings.impl.DefaultDepToArgMappingsBuilder

/**
 * @author Rinat Gareev
 */
trait DepToArgMappingsHolder {

  def containsTriggerLemma(lemmaId: Int): Boolean

  def getMappingsTriggeredBy(wf: Wordform): Iterable[DepToArgMapping]
}

trait DepToArgMappingsBuilder {

  def add(mp: DepToArgMapping)

  /**
   * Return defensive copy of current mapping collection
   */
  def getMappings(): Iterable[DepToArgMapping]

  def replace(old: DepToArgMapping, newMp: DepToArgMapping)

  def build(): DepToArgMappingsHolder

}

object DepToArgMappingsBuilder {
  def apply(): DepToArgMappingsBuilder = new DefaultDepToArgMappingsBuilder
}