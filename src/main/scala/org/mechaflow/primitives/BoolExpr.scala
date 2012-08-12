package org.mechaflow.primitives

/**
 *
 */
abstract class BoolExpr(val value: Boolean) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append value
  }
}