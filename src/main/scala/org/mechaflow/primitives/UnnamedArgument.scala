package org.mechaflow.primitives

/**
 *
 */
case class UnnamedArgument(expr: Expr) extends Argument {
  def prettyPrint(builder: StringBuilder, indent: String) {
    expr.prettyPrint(builder, indent)
  }

}