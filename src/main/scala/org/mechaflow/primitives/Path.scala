package org.mechaflow.primitives

/**
 *
 */
case class Path(path: List[Symbol]) extends Prettyable {
  def prettyPrint(builder: StringBuilder, indent: String) {
    prettySymbols(path, ".", builder, indent)
  }
}