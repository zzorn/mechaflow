package org.mechaflow.primitives

/**
 *
 */
case class FactorExpr(first: Expr, rest: List[FactorElement]) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    first.prettyPrint(builder, indent)
    prettyList(rest, "", builder, indent)
  }
}