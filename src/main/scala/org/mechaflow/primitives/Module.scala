package org.mechaflow.primitives

import org.mechaflow.parser.Scope

/**
 *
 */
case class Module(packageName: Option[Path],
                  classDefs: List[ClassDefinition]) extends Def with Prettyable {

  def buildScope(scope: Scope) {
    var s: Scope = scope

    // Create child scopes if needed
    if (packageName.isDefined) {
      packageName.get.path foreach {pn =>
        s = s.getOrAddChild(pn)
      }
    }

    // Build scope for each class
    classDefs foreach {_.buildScope(s)}
  }


  classDefs foreach {_.parent = Some(this)}

  lazy val elementsByName: Map[Symbol, Def] = (classDefs map (cd => cd.id -> cd)).toMap

  def parent = None

  protected def getLocalElement(name: Symbol) = elementsByName.get(name)

  def prettyPrint(builder: StringBuilder, indent: String) {
    if (packageName.isDefined) {
      builder append "package "
      packageName.get.prettyPrint(builder, indent)
      builder append "\n\n"
    }

    prettyList(classDefs, "\n", builder, indent)
  }
}