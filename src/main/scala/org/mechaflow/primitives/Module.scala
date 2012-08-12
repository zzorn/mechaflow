package org.mechaflow.primitives

/**
 *
 */
case class Module(packageName: Option[Path], classDefs: List[ClassDefinition]) extends Prettyable {
  def prettyPrint(builder: StringBuilder, indent: String) {
    if (packageName.isDefined) {
      builder append "package "
      packageName.get.prettyPrint(builder, indent)
      builder append "\n\n"
    }

    prettyList(classDefs, "\n", builder, indent)
  }
}