package org.mechaflow.primitives

/**
 *
 */
case class ExponentExpr(base: Expr, exponent: Expr) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    base.prettyPrint(builder, indent)
    builder append "^"
    exponent.prettyPrint(builder, indent)
  }
}