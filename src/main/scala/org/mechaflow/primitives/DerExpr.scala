package org.mechaflow.primitives

/**
 * Expression derived in relation to time.
 */
case class DerExpr(expr: Expr) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "der("
    expr.prettyPrint(builder, indent)
    builder append ")"
  }

}