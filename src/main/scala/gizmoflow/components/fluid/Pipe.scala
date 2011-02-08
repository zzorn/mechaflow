package gizmoflow.components.fluid

import scalaquantity.Units._
import gizmoflow.{FluidStuff, Gizmo}
import gizmoflow.material.{Matter, NoMatter}

/**
 * A pipe for fluids.
 */
//TODO: Divide the pipe in N cells, each cell has own composition of contained matter.
class Pipe(length: Length, radius: Length) extends Gizmo {
  
  addPort('a, FluidStuff)
  addPort('b, FluidStuff)

  val volume: Volume = length * radius * radius * math.Pi

  var containedMatter: Matter = NoMatter

  def simulateTick(time: Time) {
    // Calculate pressure at all ports, based on internal pressure and acceleration fields.

    // Solids
    // Use mass, density, and thermal expansion to calculate volume
    // There is little compressibility, so the expansion force is considerable, much more than for liquids and gasses
    // If solid matter is filling the container totally then the thermal expansion force may be enough to break it
    // on the other hand, the thermal expansion of different solid materials is relatively low, and the
    // containers elasticity and own thermal expansion is likely to hold it, so we need not model it.

    // Liquids
    // Use mass, density, and thermal expansion to calculate volume
    // If the container is completely solid and liquid filled the expansion pressure will work directly on the container

    // Gases
    // Use the ideal gas law to calculate the pressure from the mass, volume, and temperature.


    


    // Update pressure of contained matter
    //containedMatter.pressure

    // Calculate volume of the contained matter - depends on its density and temperature expansion, and also on the current pressure
    //containedMatter.volume
    //containedMatter.mass

  }

  def simulateTock(time: Time) {

    // When pressure at both sides of a port, the radiuses of the ports,
    // and the viscosity of the matter are known, the flow rate between them can be calculated.
    // Multiply flow rate with timestep size to determine the amount of matter to move.
    // Remove the matter from the source and add it to the destination



/*
    ports('a).getConnectedPort match {
      case None =>
      case Some(p) => p.
    }
*/
  }
}
