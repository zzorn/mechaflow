package gizmoflow.components.fluid

import gizmoflow.{FluidStuff, Gizmo}

/**
 * Can be used to lead away fluid.
 */
class FluidSink extends Gizmo {
  addPort('in, FluidStuff)
}