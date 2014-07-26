package org.mechaflow.parser

import org.mechaflow.primitives.{Node, Path, Def}


/**
 * Lookup table for symbols in scope.
 */
class Scope {

  private var parentScopes: List[Scope] = Nil
  private var nodes     = Map[Symbol, Node]()
  private var subScopes = Map[Symbol, Scope]()

  def addParent(parent: Scope) {
    require(parent != null)
    if (parentScopes.contains(parent)) throw new Error("The parent already exists in the scope.")

    parentScopes ::= parent
  }

  def getOrAddChild(name: Symbol): Scope = {
    if (subScopes.contains(name)) subScopes(name)
    else {
      val child = new Scope()
      addChild(name, child)
      child
    }
  }

  def addChild(name: Symbol, child: Scope) {
    require(name != null)
    require(child != null)
    if (nodes.contains(name)) throw new Error("A node named '"+name.name+"' already exists in the scope.")
    if (subScopes.contains(name)) throw new Error("A sub scope named '"+name.name+"' already exists in the scope.")

    subScopes += name -> child
  }

  def addNode(name: Symbol, node: Node) {
    require(name != null)
    require(node != null)
    if (nodes.contains(name)) throw new Error("A node named '"+name.name+"' already exists in the scope.")
    if (subScopes.contains(name)) throw new Error("A sub scope named '"+name.name+"' already exists in the scope.")

    nodes += name -> node
  }

  final def getLocalNode(name: Symbol): Option[Node] = {
    nodes.get(name)
  }

  final def getNode(path: List[Symbol], canBacktrack: Boolean = true): Option[Node] = {
    path match {
      case Nil => None
      case head :: tail => {
        // Look at local nodes
        if (nodes.contains(head) && tail == Nil) nodes.get(head)

        // Look at sub scopes
        else if (subScopes.contains(head) && tail != Nil) {
          subScopes(head).getNode(tail, canBacktrack = false)
        }

        // Delegate to parents
        else if (canBacktrack) {
          def delegateToParents(parents: List[Scope], path: List[Symbol]): Option[Node] = {
            parents match {
              case Nil => None
              case h :: t =>
                val result = h.getNode(path)
                if (result.isDefined) result
                else delegateToParents(t, path)
            }
          }

          delegateToParents(parentScopes, path)
        }

        else None
      }
    }
  }

  final def getScope(path: List[Symbol], canBacktrack: Boolean = true): Option[Scope] = {
    path match {
      case Nil => None
      case head :: tail => {
        // Look at this scope
        if (path == Nil) Some(this)

        // Look under sub scopes
        else if (subScopes.contains(head)) {
          subScopes(head).getScope(tail, canBacktrack = false)
        }

        // Delegate to parents
        else if (canBacktrack) {
          def delegateToParents(parents: List[Scope], path: List[Symbol]): Option[Scope] = {
            parents match {
              case Nil => None
              case h :: t =>
                val result = h.getScope(path)
                if (result.isDefined) result
                else delegateToParents(t, path)
            }
          }

          delegateToParents(parentScopes, path)
        }

        else None
      }
    }
  }



}
