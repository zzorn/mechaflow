package org.mechaflow.primitives

/**
 *
 */
trait Assignment extends Prettyable {

}

case class ExpressionAssignment(expression: Expr) extends Assignment {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "= "
    expression.prettyPrint(builder, indent)
  }
}