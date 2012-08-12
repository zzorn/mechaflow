package org.mechaflow.primitives

/**
 *
 */
case class TermExpr(first: Expr, rest: List[TermElement]) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    first.prettyPrint(builder, indent)
    prettyList(rest, "", builder, indent)
  }
}