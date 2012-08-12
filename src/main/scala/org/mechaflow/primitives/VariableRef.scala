package org.mechaflow.primitives

/**
 *
 */
case class VariableRef(variableRef: Path) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    variableRef.prettyPrint(builder, indent)
  }
}