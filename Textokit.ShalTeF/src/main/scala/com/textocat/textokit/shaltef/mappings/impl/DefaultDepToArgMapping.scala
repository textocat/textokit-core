package com.textocat.textokit.shaltef.mappings.impl

import com.textocat.textokit.shaltef.mappings.{DepToArgMapping, SlotMapping}
import org.apache.commons.lang3.builder.{HashCodeBuilder, ToStringBuilder, ToStringStyle}
import org.apache.uima.cas.Type

/**
 * @author Rinat Gareev
 */
private[mappings] class DefaultDepToArgMapping(val templateAnnoType: Type,
                                               val triggerLemmaIds: Set[Int], val slotMappings: List[SlotMapping])
  extends DepToArgMapping {

  override def equals(obj: Any): Boolean =
    obj match {
      case that: DefaultDepToArgMapping => this.templateAnnoType == that.templateAnnoType &&
        this.triggerLemmaIds == that.triggerLemmaIds && this.slotMappings == that.slotMappings
      case _ => false
    }

  override def hashCode(): Int =
    new HashCodeBuilder().append(templateAnnoType).append(triggerLemmaIds).append(slotMappings)
      .toHashCode()

  override def toString: String = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
    append(templateAnnoType).append(triggerLemmaIds).append(slotMappings).toString;
}