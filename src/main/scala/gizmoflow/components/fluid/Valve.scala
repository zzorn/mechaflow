package gizmoflow.components.fluid

import gizmoflow.{FluidStuff, Gizmo}

/**
 * One way valve
 */
class Valve extends Gizmo {
  
  addPort('inflow, FluidStuff)
  addPort('outflow, FluidStuff)

}