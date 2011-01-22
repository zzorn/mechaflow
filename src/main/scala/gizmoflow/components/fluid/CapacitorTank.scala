package gizmoflow.components.fluid
import scalaquantity.Units._
import gizmoflow.{FluidStuff, Gizmo}
import gizmoflow.material.{NoMatter, Matter}

/**
 * A tank with an elastic membrane across the middle, acting as a capacitor.
 */
class CapacitorTank(val volume: Volume) extends Gizmo {

  addPort('a, FluidStuff)
  addPort('b, FluidStuff)

  var containedMatterOnASide: Matter = NoMatter
  var containedMatterOnBSide: Matter = NoMatter

}