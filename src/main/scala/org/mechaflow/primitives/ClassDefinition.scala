package org.mechaflow.primitives

/**
 *
 */
case class ClassDefinition(id: Symbol,
                           doc: Option[String],
                           imports: List[Import],
                           extensions: List[Extends],
                           elements: List[Element],
                           equations: List[Equation])  extends Prettyable {

  def prettyPrint(builder: StringBuilder, indent: String) {
    val ci = indent + "  "
    val ei = indent + "    "

    builder append indent
    builder append "class "
    builder append id.name
    if (doc.isDefined) {
      builder append " \""
      builder append doc.get
      builder append "\""
    }
    builder append " {\n"

    prettyList(imports,    ";\n" + ci, builder, ci, ci, ";\n")
    prettyList(extensions, ";\n" + ci, builder, ci, ci, ";\n")
    prettyList(elements,   ";\n" + ci, builder, ci, ci, ";\n")
    prettyList(equations,  ";\n" + ei, builder, ei, ci+"equations\n"+ei, ";\n")

    builder append indent append "}\n"
  }
}