package org.mechaflow.primitives

/**
 *
 */
case class Element(id: Symbol,
                   typePath: Path,
                   initialValue: Option[Assignment],
                   doc: Option[String],
                   flow: Boolean = false,
                   in: Boolean = false,
                   out: Boolean = false,
                   const: Boolean = false,
                   param: Boolean = false) extends Prettyable {

  def prettyPrint(builder: StringBuilder, indent: String) {
    if (flow) builder append "flow "
    if (in) builder append "in "
    if (out) builder append "output "
    if (in) builder append "input "

    if (const) builder append "const "
    else if (param) builder append "param "
    else builder append "var "

    builder append id.name
    builder append " "
    typePath.prettyPrint(builder, indent)
    builder append " "
    if (initialValue.isDefined) {
      initialValue.get.prettyPrint(builder, indent)
    }
    builder append " "
    if (doc.isDefined) {
      builder append "\""
      builder append doc.get
      builder append "\""
    }
  }
}