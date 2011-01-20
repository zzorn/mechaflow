package gizmoflow.components.fluid

import scalaquantity.Units._
import gizmoflow.{FluidStuff, Node}
import gizmoflow.material.{Matter, NoMatter}

/**
 * A pipe in a T junction.  Can have a connection in each direction.
 */
class Junction(radius: Length) extends Node  {

  addPort('a, FluidStuff)
  addPort('b, FluidStuff)
  addPort('c, FluidStuff)
  addPort('d, FluidStuff)

  var containedMatter: Matter = NoMatter

}
