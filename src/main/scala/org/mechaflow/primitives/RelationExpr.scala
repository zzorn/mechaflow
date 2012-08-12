package org.mechaflow.primitives

/**
 *
 */
case class RelationExpr(a: Expr, op: Symbol, b: Expr) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    a.prettyPrint(builder, indent)
    builder append " "
    builder append op.name
    builder append " "
    b.prettyPrint(builder, indent)

  }
}