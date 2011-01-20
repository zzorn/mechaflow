package gizmoflow.components.fluid

import scalaquantity.Units._
import gizmoflow.material.{NoMatter, Matter}
import gizmoflow.{FluidStuff, Node}

/**
 * A fluid container.
 */
//TODO: Divide the reservoir in N layers, each layer has own composition of contained matter.
case class Reservoir(volume: Volume) extends Node {

  addPort('top, FluidStuff)
  addPort('middle, FluidStuff)
  addPort('bottom, FluidStuff)

  var containedMatter: Matter = NoMatter

}
