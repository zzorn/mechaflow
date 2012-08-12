package org.mechaflow.primitives

/**
 *
 */
case class OrExpr(logicalTerms: List[Expr]) extends Expr  {
  def prettyPrint(builder: StringBuilder, indent: String) {
    prettyList(logicalTerms, " or ", builder, indent)
  }

}