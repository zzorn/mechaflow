package gizmoflow.components.fluid

import gizmoflow.{FluidStuff, Gizmo}

/**
 * A pipe with attached flywheel, acts as an inductor.
 */
// TODO: What unit to specify inductance in here?
class FlywheelPipe(inductance: Double) extends Gizmo {

  addPort('a, FluidStuff)
  addPort('b, FluidStuff)

}