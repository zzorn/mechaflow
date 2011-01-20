package gizmoflow.components.fluid

import gizmoflow.{FluidStuff, Node}

/**
 * A pipe with attached flywheel, acts as an inductor.
 */
// TODO: What unit to specify inductance in here?
class FlywheelPipe(inductance: Double) extends Node {

  addPort('a, FluidStuff)
  addPort('b, FluidStuff)

}