package gizmoflow.components.fluid

import scalaquantity.Units._
import gizmoflow.material.{NoMatter, Matter}
import gizmoflow.{FluidStuff, Gizmo}

/**
 * A fluid container.
 */
//TODO: Divide the reservoir in N layers, each layer has own composition of contained matter.
case class Reservoir(volume: Volume) extends Gizmo {

  addPort('top, FluidStuff)
  addPort('middle, FluidStuff)
  addPort('bottom, FluidStuff)

  var containedMatter: Matter = NoMatter

}
