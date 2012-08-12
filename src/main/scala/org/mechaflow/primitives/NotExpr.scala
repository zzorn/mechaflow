package org.mechaflow.primitives

/**
 *
 */
case class NotExpr(expr: Expr) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "not "
    expr.prettyPrint(builder, indent)
  }
}