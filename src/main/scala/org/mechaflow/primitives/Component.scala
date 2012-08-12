package org.mechaflow.primitives

/**
 * May have variables (visible outside or not).
 * May have equations.
 */
case class Component(name: Symbol,
                     desc: String = null,
                     parents: List[Component] = Nil,
                     connector: Boolean = false,
                     var variables: List[Var] = Nil,
                     var equations: List[Equation] = Nil) extends Value {

  var variablesByName: Map[Symbol, Var] = variables.map(v => v.name -> v).toMap

  def addVar(variable: Var): Var = {
    require(variable != null)
    if (variablesByName.contains(variable.name)) throw new Error("Variable named "+variable.name.name +" already defined in " + name.name)

    variables ::= variable
    variablesByName += variable.name -> variable
    variable
  }

  def setVar(variableName: Symbol, value: Value) {
    // Check
    require(variableName != null)
    require(value != null)
    if (!variablesByName.contains(variableName)) throw new Error("No variable named "+variableName.name +" found in " + name.name)
    if (!variablesByName(variableName).parameter) throw new Error("The variable named "+variableName.name +" is not a parameter, can not assign a new value to it.")

    // Set value
    variablesByName(variableName).value = value
  }

  def addEq(equation: Equation): Equation = {
    require(equation != null)

    equations ::= equation; equation
  }

  def dot(name: Symbol): Var = variablesByName(name)

  def isConnector: Boolean = {
    connector || parents.exists(_.isConnector)
  }

  /**
   * Connects two specified components, that should be connectors.
   */
  def connect(from: Path, to: Path) {
    //addEq(Connection(from, to))
  }

  def createCopy: Value = copy()

  def newInstance(variableValues: Map[Symbol, Value] = Map()): Component = {
    val n = createCopy.asInstanceOf[Component]

    // Deep copy variables
    n.variables = variables.map(_.createCopy())

    // Assign parameter values
    variableValues foreach {v =>
      val name = v._1
      val value = v._2
      n.setVar(name, value)
    }

    n
  }
}
