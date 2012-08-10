package org.mechaflow.primitives

/**
 * Real value number.
 */
case class Real(value: Double = 0.0) extends Value {

  def dot(name: Symbol) = throw new Error("Real values have no memebers")

  def createCopy = this
}
