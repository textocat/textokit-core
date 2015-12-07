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