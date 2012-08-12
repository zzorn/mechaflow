package org.mechaflow.primitives

/**
 *
 */
case class ParsedEquation(equationAsText: String) extends Equation {
  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "eq "
    builder append equationAsText
  }
}
