package gizmoflow.components.fluid

import gizmoflow.{FluidStuff, Gizmo}

/**
 * A source of some kind of fluid.
 */
class FluidSource extends Gizmo {
  addPort('out, FluidStuff)
}