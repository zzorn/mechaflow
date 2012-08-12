package org.mechaflow.primitives

/**
 *
 */
case class AndExpr(logicalFactors: List[Expr]) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    prettyList(logicalFactors, " and ", builder, indent)
  }
}
