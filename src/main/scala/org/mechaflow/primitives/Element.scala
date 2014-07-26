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
                   param: Boolean = false) extends Def with Prettyable {


  var parent: Option[Def] = None

  protected def getLocalElement(name: Symbol) = None

  def prettyPrint(builder: StringBuilder, indent: String) {

    // Modifiers
    if (flow) builder append "flow "
    if (in) builder append "in "
    if (out) builder append "output "
    if (in) builder append "input "

    // Category of element
    if (const) builder append "const "
    else if (param) builder append "param "
    else builder append "var "

    // Type
    typePath.prettyPrint(builder, indent)

    // Identifier
    builder append " "
    builder append id.name

    // Initial value
    if (initialValue.isDefined) {
      builder append " "
      initialValue.get.prettyPrint(builder, indent)
    }

    // Doc string
    if (doc.isDefined) {
      builder append " "
      builder append "\""
      builder append doc.get
      builder append "\""
    }
  }
}