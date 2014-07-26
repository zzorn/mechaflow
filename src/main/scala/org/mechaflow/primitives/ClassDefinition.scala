package org.mechaflow.primitives

import org.mechaflow.parser.Scope

/**
 *
 */
case class ClassDefinition(id: Symbol,
                           doc: Option[String],
                           imports: List[Import],
                           extensions: List[Extends],
                           elements: List[Element],
                           equations: List[Equation])  extends Def with Prettyable {

  def buildScope(scope: Scope) {
    // TODO: Handle imports

    // Add elements to scope
    elements foreach {e =>
      scope.addNode(e.id, e)
    }
  }


  elements foreach {_.parent = Some(this)}

  var parent: Option[Def] = None

  lazy val elementsByName: Map[Symbol, Element] = (elements map(e => e.id -> e)).toMap

  protected def getLocalElement(name: Symbol) = elementsByName.get(name)

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