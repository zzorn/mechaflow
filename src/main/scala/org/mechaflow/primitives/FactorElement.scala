package org.mechaflow.primitives

/**
 *
 */
case class FactorElement(op: Symbol, expr: Expr) extends Prettyable {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append " "
    builder append op.name
    builder append " "
    expr.prettyPrint(builder, indent)
  }

}