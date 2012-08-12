package org.mechaflow.primitives

/**
 *
 */
case class NumExpr(value: Double) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append value
  }
}