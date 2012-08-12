package org.mechaflow.primitives

/**
 *
 */
case class IfExpr(condition: Expr, thenExpr: Expr, elseExpr: Expr) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "if "
    condition.prettyPrint(builder, indent)
    builder append " then "
    thenExpr.prettyPrint(builder, indent)
    builder append " else "
    elseExpr.prettyPrint(builder, indent)
  }

}