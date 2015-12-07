package com.textocat.textokit.shaltef.mappings.pattern

import com.textocat.textokit.morph.dictionary.resource.GramModel

/**
 * @author Rinat Gareev
 */
class ConstraintValueFactory(gramModel: GramModel) {
  def constant(valueString: String): ConstraintValue = ConstantScalarValue(valueString)

  def constantCollection(values: Iterable[String]): ConstraintValue = ConstantCollectionValue(values)

  def constantCollectionAlternatives(colValues: Set[Iterable[String]]): ConstraintValue =
    ConstantCollectionAlternatives(colValues)

  def triggerFeatureReference(refString: String): ConstraintValue = {
    getGramCategory(gramModel, refString) match {
      case Some(gramCatSet) => new TriggerGrammemeReference(gramCatSet)
      case None => throw new IllegalArgumentException(
        "Unknown trigger feature reference: %s".format(refString))
    }
  }
}

private[pattern] case class ConstantScalarValue(valueString: String) extends ConstraintValue {
  override def getValue(ctx: MatchingContext) = valueString
}

private[pattern] case class ConstantCollectionValue(values: Iterable[String])
  extends ConstraintValue {
  override def getValue(ctx: MatchingContext) = values
}

private[pattern] case class ConstantCollectionAlternatives(colValues: Set[Iterable[String]])
  extends ConstraintValue {
  override def getValue(ctx: MatchingContext) = colValues
}

private[pattern] case class TriggerGrammemeReference(gramIds: Set[String])
  extends ConstraintValue with GrammemeExtractor {

  override def getValue(ctx: MatchingContext): String = extractGrammeme(ctx.triggerHead)
}