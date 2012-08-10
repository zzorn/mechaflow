package org.mechaflow.primitives

/**
 *
 */
case class Var(name: Symbol, var value: Value, flow: Boolean = false, parameter: Boolean = false) {

  def createCopy(): Var = {
    Var(name, value.createCopy, flow, parameter)
  }

}
