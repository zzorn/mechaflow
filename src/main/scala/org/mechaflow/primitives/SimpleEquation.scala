package org.mechaflow.primitives

/**
 *
 */
case class SimpleEquation(left: Expr, right: Expr) extends Equation {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "eq "
    left.prettyPrint(builder, indent)
    builder append " = "
    right.prettyPrint(builder, indent)
  }
}
