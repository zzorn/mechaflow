package org.mechaflow.primitives

/**
 * Function call expression
 */
case class CallExpr(function: Path, args: List[Argument]) extends Expr {
  def prettyPrint(builder: StringBuilder, indent: String) {
    function.prettyPrint(builder, indent)
    builder append "("
    prettyList(args, ", ", builder, indent)
    builder append ")"
  }
}