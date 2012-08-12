package org.mechaflow.primitives

/**
 *
 */
case class Import(path: Path) extends Prettyable {

  def prettyPrint(builder: StringBuilder, indent: String) {
    builder append "import "
    path.prettyPrint(builder, indent)
  }

}