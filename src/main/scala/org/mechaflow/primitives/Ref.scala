package org.mechaflow.primitives

/**
 *
 */
case class Ref(path: List[Symbol]) extends Prettyable {
  def prettyPrint(builder: StringBuilder, indent: String) {
    prettySymbols(path, ".", builder, indent)
  }
}