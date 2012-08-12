package org.mechaflow.primitives

/**
 *
 */
case class NamedArgument(name: Symbol, expr: Expr) extends Argument {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append name.name
    builder append " = "
    expr.prettyPrint(builder, indent)
  }
}