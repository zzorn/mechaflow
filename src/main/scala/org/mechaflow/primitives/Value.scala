package org.mechaflow.primitives

/**
 *
 */
trait Value {

  /**
   * @return the variable with the specified name from inside this value if it has one, otherwise throws error.
   */
  def dot(name: Symbol): Var

  /**
   *
   * @return a unique instance of this value, if the value has state.
   */
  def createCopy: Value

}
