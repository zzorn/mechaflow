package org.mechaflow.primitives

/**
 * Function call expression
 */
case class CallExpr(function: Path, args: List[Argument]) extends Expr {

}