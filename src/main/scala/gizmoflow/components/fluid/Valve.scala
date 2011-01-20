package gizmoflow.components.fluid

import gizmoflow.{FluidStuff, Node}

/**
 * One way valve
 */
class Valve extends Node {
  
  addPort('inflow, FluidStuff)
  addPort('outflow, FluidStuff)

}