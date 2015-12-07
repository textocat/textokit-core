/**
 *
 */
package ru.kfu.itis.issst.uima.shaltef.mappings

/**
 * @author Rinat Gareev
 *
 */
trait DepToArgMappingsPostProcessor {
  
  def postprocess(mpBuilder:DepToArgMappingsBuilder)

}