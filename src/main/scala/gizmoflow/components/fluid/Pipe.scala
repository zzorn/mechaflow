package gizmoflow.components.fluid

import scalaquantity.Units._
import gizmoflow.{FluidStuff, Node}
import gizmoflow.material.{Matter, NoMatter}

/**
 * A pipe for fluids.
 */
//TODO: Divide the pipe in N cells, each cell has own composition of contained matter.
class Pipe(length: Length, radius: Length) extends Node {
  
  addPort('a, FluidStuff)
  addPort('b, FluidStuff)

  var containedMatter: Matter = NoMatter

}
