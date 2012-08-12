package org.mechaflow.primitives

/**
 *
 */
case class TermElement(op: Symbol, term: Expr) extends Prettyable {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append " "
    builder append op.name
    builder append " "
    term.prettyPrint(builder, indent)
  }
}