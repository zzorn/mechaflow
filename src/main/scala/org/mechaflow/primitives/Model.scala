package org.mechaflow.primitives

/**
 * May have parameters.
 * May have variables (visible outside or not).
 * May have equations.
 */
class Model(val name: Symbol) {

  private var modifiers: List[Symbol] = Nil
  private var parents: List[Model] = Nil
  private var parameters: Map[Symbol, Var] = Map()
  private var variables: Map[Symbol, Var] = Map()
  private var equations: List[Equation] = Nil

  def addParameter(name: Symbol, defaultValue: Value, modifiers: List[Symbol] = Nil) {parameters += name -> Var(name, defaultValue, modifiers)}
  def addVariable(name: Symbol, startValue: Value, modifiers: List[Symbol] = Nil) {variables += name -> Var(name, startValue, modifiers)}
  def addEquation(equation: Equation) {equations ::= equation}
  def addParent(parent: Model) {parents ::= parent}
  def addModifier(modifier: Symbol) {modifiers ::= modifier}
}
