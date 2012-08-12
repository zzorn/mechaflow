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

    builder append indent
    builder append "class "
    builder append id.name
    if (doc.isDefined) {
      builder append " \""
      builder append doc.get
      builder append "\""
    }
    builder append " {\n" append ci

    prettyList(imports, "\n" + ci, builder, ci, addSeparatorAfterLast = true)
    prettyList(extensions, "\n" + ci, builder, ci, addSeparatorAfterLast = true)
    prettyList(elements, "\n" + ci, builder, ci, addSeparatorAfterLast = true)
    prettyList(equations, "\n" + ci, builder, ci, addSeparatorAfterLast = true)

    builder append "\n" append indent append "}\n"
  }
}