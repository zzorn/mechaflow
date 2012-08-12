package org.mechaflow.primitives

/**
 *
 */
case class Extends(path: Path) extends Prettyable {

  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "extends "
    path.prettyPrint(builder, indent)
  }
}