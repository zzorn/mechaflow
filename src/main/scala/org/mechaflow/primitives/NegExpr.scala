package org.mechaflow.primitives

/**
 *
 */
case class NegExpr(expr: Expr) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "-"
    expr.prettyPrint(builder, indent)
  }
}