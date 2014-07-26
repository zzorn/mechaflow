package org.mechaflow.primitives

/**
 * Definition that may also have named child definitions.
 */
trait Def extends Node {

  def parent: Option[Def]

  protected def getLocalElement(name: Symbol): Option[Def]

  final def getElement(e: Symbol): Option[Def] = {
    getLocalElement(e).orElse(parent.flatMap(_.getElement(e)))
  }

  final def getPath(path: List[Symbol]): Option[Def] = {
    path match {
      case Nil => None
      case head :: tail => {
        getElement(head).flatMap(_.getLocalPath(tail))
      }
    }
  }

  private final def getLocalPath(path: List[Symbol]): Option[Def] = {
    path match {
      case Nil => Some(this)
      case head :: tail => {
        getLocalElement(head).flatMap(_.getLocalPath(tail))
      }
    }
  }


}
